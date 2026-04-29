(ns telsos.lucene-svc.handlers.delete
  (:require
   [telsos.lib.call :as call]
   [telsos.lucene-svc.index :as idx]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private DeleteRequest
  [:map {:closed true}
   [:index-name [:string {:min 1}]]
   [:query      [:string {:min 1}]]])

(def ^:private DeleteResponse
  [:map {:closed true}
   [:deleted-count :int]])

(defn- delete-logic [_ctx {:keys [index-name query]}]
  {:deleted-count (idx/delete-by-query! index-name query)})

(let [h (call/create-json-edn-handler 200 DeleteRequest delete-logic DeleteResponse)]
  (def delete-handler (fn [request] (h nil request))))
