(ns telsos.lucene-svc.http.routes
  (:require
   [telsos.lucene-svc.handlers.add :as add-h]
   [telsos.lucene-svc.handlers.clear :as clear-h]
   [telsos.lucene-svc.handlers.delete :as delete-h]
   [telsos.lucene-svc.handlers.search :as search-h]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(def routes
  [["/index/add"
    {:post {:handler  #'add-h/add-handler
            :produces ["application/json"]
            :consumes ["application/json"]}}]

   ["/index/delete"
    {:post {:handler  #'delete-h/delete-handler
            :produces ["application/json"]
            :consumes ["application/json"]}}]

   ["/index/clear"
    {:post {:handler  #'clear-h/clear-handler
            :produces ["application/json"]
            :consumes ["application/json"]}}]

   ["/index/search"
    {:post {:handler  #'search-h/search-handler
            :produces ["application/json"]
            :consumes ["application/json"]}}]])
