(ns telsos.lucene-svc.handlers.clear
  (:require
   [telsos.lib.call :as call]
   [telsos.lucene-svc.index :as idx]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private ClearRequest
  [:map {:closed true}
   [:index-name [:string {:min 1}]]])

(def ^:private ClearResponse
  [:map {:closed true}
   [:ok :boolean]])

(defn- clear-logic [_ctx {:keys [index-name]}]
  (idx/clear! index-name)
  {:ok true})

(let [h (call/create-json-edn-handler 200 ClearRequest clear-logic ClearResponse)]
  (def clear-handler (fn [request] (h nil request))))
