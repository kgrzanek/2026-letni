(ns telsos.lucene-svc.http.service
  (:require
   [telsos.lucene-svc.config :as config]
   [telsos.lucene-svc.http]
   [telsos.lucene-svc.http.routes :refer [routes]]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(defonce jetty
  (delay (telsos.lucene-svc.http/jetty-start!
           (-> routes
               telsos.lucene-svc.http/reitit-router
               telsos.lucene-svc.http/reitit-handler)

           {:port                 config/http-port
            :join?                false
            :use-virtual-threads? true})))

(defn ns-finalize []
  (when (realized? jetty)
    (java.io.Closeable/.close @jetty)))

;; curl -s -X POST http://localhost:8181/index/add \
;;   -H 'Content-Type: application/json' \
;;   -d '{"index-name": "my-index", "documents": [{"title": "Hello World", "body": "This is a test document"}]}' \
;;   | jq .

;; curl -s -X POST http://localhost:8181/index/search \
;;     -H 'Content-Type: application/json' \
;;     -d '{"index-name": "my-index", "query": "test", "max-results": 10}' \
;;     | jq .

;; curl -s -X POST http://localhost:8181/index/search \
;;     -H 'Content-Type: application/json' \
;;     -d '{"index-name": "my-index", "query": "body:test", "max-results": 10}' \
;;     | jq .
