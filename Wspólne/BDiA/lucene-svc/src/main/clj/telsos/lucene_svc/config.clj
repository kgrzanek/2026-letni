(ns telsos.lucene-svc.config
  (:require
   [telsos.lib.assertions :refer [the]]
   [telsos.lib.io]
   [telsos.lib.strings :refer [non-blank?]]))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(defonce config
  (->> "config.edn" telsos.lib.io/read-resource-edn (the map?)))

(def lucene-index-base-path
  (->> config :lucene :index-base-path (the non-blank?)))

(def http-port
  (->> config :http :port (the pos-int?)))
