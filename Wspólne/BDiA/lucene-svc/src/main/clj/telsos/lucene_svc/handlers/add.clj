(ns telsos.lucene-svc.handlers.add
  (:require
   [telsos.lib.call :as call]
   [telsos.lucene-svc.index :as idx]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(def ^:private AddRequest
  [:map {:closed true}
   [:index-name [:string {:min 1}]]
   ;; muuntaja keywordizes JSON object keys, so document maps arrive with keyword keys
   [:documents  [:vector {:min 1} [:map-of :keyword :string]]]])

(def ^:private AddResponse
  [:map {:closed true}
   [:indexed-count :int]])

(defn- add-logic [_ctx {:keys [index-name documents]}]
  ;; Convert keyword keys → string keys for Lucene field names
  {:indexed-count (idx/add-documents! index-name (map #(update-keys % name) documents))})

(let [h (call/create-json-edn-handler 200 AddRequest add-logic AddResponse)]
  (def add-handler (fn [request] (h nil request))))
