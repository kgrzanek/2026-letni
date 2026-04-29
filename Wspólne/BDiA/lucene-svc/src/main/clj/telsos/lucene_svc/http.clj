(ns telsos.lucene-svc.http
  (:require
   [muuntaja.core :as muuntaja]
   [reitit.ring]
   [reitit.ring.middleware.muuntaja]
   [reitit.ring.middleware.parameters]
   [ring.adapter.jetty]
   [telsos.lib.logging :as log])
  (:import
   (java.util.concurrent Executors)
   (org.eclipse.jetty.server Server)
   (org.eclipse.jetty.util.thread QueuedThreadPool)))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(defn reitit-router
  [routes]
  (reitit.ring/router
    routes
    {:data {:muuntaja   muuntaja/instance
            :middleware [reitit.ring.middleware.parameters/parameters-middleware
                         reitit.ring.middleware.muuntaja/format-middleware]}}))

(defn reitit-handler
  ([router]
   (reitit-handler router nil))

  ([router {:keys [resources-path resources-root] :as options}]
   (reitit.ring/ring-handler
     router
     (reitit.ring/routes
       (reitit.ring/redirect-trailing-slash-handler)
       (when resources-path
         (reitit.ring/create-resource-handler
           (merge {:path resources-path}
                  (when resources-root {:root resources-root}))))
       (reitit.ring/create-default-handler))
     (dissoc options :resources-path :resources-root))))

(declare jetty-stop!)

(defrecord ^:private Jetty [jetty]
  java.io.Closeable
  (close [_this] (jetty-stop! jetty)))

(defn jetty-start!
  [handler {:keys [port join? use-virtual-threads?]
            :or   {port                 8080
                   join?                false
                   use-virtual-threads? false}}]
  (when-not (pos-int? port)
    (throw (ex-info "Illegal Jetty port specified" {:port port})))
  (let [jetty
        (ring.adapter.jetty/run-jetty
          handler
          (merge {:port  port
                  :join? (boolean join?)
                  :max-form-content-size (* 10 1024 1024)}
                 (when use-virtual-threads?
                   {:thread-pool
                    (doto (QueuedThreadPool.)
                      (QueuedThreadPool/.setVirtualThreadsExecutor
                        (Executors/newVirtualThreadPerTaskExecutor)))})))]
    (log/info jetty "started")
    (->Jetty jetty)))

(defn- jetty-stop!
  [jetty]
  (if (Server/.isRunning jetty)
    (do (Server/.stop jetty)
        (log/info jetty "successfully stopped"))
    (log/info jetty "has already been stopped")))
