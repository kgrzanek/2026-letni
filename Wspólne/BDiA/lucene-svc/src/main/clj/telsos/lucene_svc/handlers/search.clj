(ns telsos.lucene-svc.handlers.search
  (:require
   [telsos.lib.call :as call]
   [telsos.lucene-svc.index :as idx]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private SearchRequest
  [:map {:closed true}
   [:index-name  [:string {:min 1}]]
   [:query       [:string {:min 1}]]
   [:max-results [:int {:min 1 :max 1000}]]])

(def ^:private SearchResponse
  [:map {:closed true}
   [:results [:vector [:map-of :string :string]]]])

(defn- search-logic [_ctx {:keys [index-name query max-results]}]
  {:results (idx/search! index-name query (long max-results))})

(let [h (call/create-json-edn-handler 200 SearchRequest search-logic SearchResponse)]
  (def search-handler (fn [request] (h nil request))))
