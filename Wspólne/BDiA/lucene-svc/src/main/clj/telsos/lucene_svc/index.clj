(ns telsos.lucene-svc.index
  (:require
   [telsos.lib.logging :as log]
   [telsos.lucene-svc.config :as config])
  (:import
   (java.nio.file Files Paths)
   (java.nio.file.attribute FileAttribute)
   (org.apache.lucene.analysis.standard StandardAnalyzer)
   (org.apache.lucene.document Document Field$Store TextField)
   (org.apache.lucene.index IndexWriter IndexWriterConfig)
   (org.apache.lucene.queryparser.classic QueryParser)
   (org.apache.lucene.search IndexSearcher ScoreDoc SearcherManager TopDocs)
   (org.apache.lucene.store FSDirectory)))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

;; Named-index state: index-name -> {:writer IndexWriter :searcher-mgr SearcherManager}
(def ^:private indices (atom {}))

(defn- index-dir-path
  ^java.nio.file.Path [^String index-name]
  (Paths/get config/lucene-index-base-path (into-array String [index-name])))

(defn- open-index!
  [^String index-name]
  (let [path (index-dir-path index-name)
        _    (Files/createDirectories path (into-array FileAttribute []))
        dir  (FSDirectory/open path)
        cfg  (IndexWriterConfig. (StandardAnalyzer.))
        w    (IndexWriter. dir cfg)
        smgr (SearcherManager. w true false nil)]
    (log/info "Opened Lucene index" index-name)
    {:writer      w
     :searcher-mgr smgr}))

(defn- get-or-open-index!
  [^String index-name]
  (or (get @indices index-name)
      (locking indices
        (or (get @indices index-name)
            (let [entry (open-index! index-name)]
              (swap! indices assoc index-name entry)
              entry)))))

(defn- num-docs ^long [^IndexWriter w]
  ;; IndexWriter.numDocs() was removed in Lucene 10; use getDocStats().numDocs
  (.-numDocs (.getDocStats w)))

(defn- map->doc ^Document [m]
  (let [doc (Document.)]
    (doseq [[k v] m]
      (.add doc (TextField. ^String k ^String v Field$Store/YES)))
    doc))

(defn- doc->map [^Document doc]
  (reduce (fn [m ^org.apache.lucene.index.IndexableField f]
            (assoc m (.name f) (.stringValue f)))
          {}
          (.getFields doc)))

(defn- parse-query
  ^org.apache.lucene.search.Query [^String query-str]
  (.parse (QueryParser. "_text_" (StandardAnalyzer.)) query-str))

(defn add-documents!
  "Adds documents (seq of string-keyed maps) to named index. Returns count added."
  ^long [^String index-name documents]
  (let [idx              (get-or-open-index! index-name)
        ^IndexWriter     w    (:writer idx)
        ^SearcherManager smgr (:searcher-mgr idx)]
    (doseq [doc documents]
      (.addDocument w (map->doc doc)))
    (.commit w)
    (.maybeRefresh smgr)
    (count documents)))

(defn delete-by-query!
  "Deletes documents matching query-str from named index. Returns approximate deleted count."
  ^long [^String index-name ^String query-str]
  (let [idx              (get-or-open-index! index-name)
        ^IndexWriter     w    (:writer idx)
        ^SearcherManager smgr (:searcher-mgr idx)
        before           (num-docs w)
        ^"[Lorg.apache.lucene.search.Query;" qs
        (into-array org.apache.lucene.search.Query [(parse-query query-str)])]
    (.deleteDocuments w qs)
    (.commit w)
    (.maybeRefresh smgr)
    (- before (num-docs w))))

(defn clear!
  "Deletes all documents in named index."
  [^String index-name]
  (let [idx              (get-or-open-index! index-name)
        ^IndexWriter     w    (:writer idx)
        ^SearcherManager smgr (:searcher-mgr idx)]
    (.deleteAll w)
    (.commit w)
    (.maybeRefresh smgr)))

(defn search!
  "Searches named index for query-str. Returns up to max-results maps."
  [^String index-name ^String query-str ^long max-results]
  (let [^SearcherManager smgr    (:searcher-mgr (get-or-open-index! index-name))
        ^IndexSearcher   searcher (.acquire smgr)]
    (try
      (let [^TopDocs top-docs (.search searcher (parse-query query-str) (int max-results))
            stored-fields     (.storedFields searcher)]
        (mapv (fn [^ScoreDoc sd]
                (doc->map (.document stored-fields (.-doc sd))))
              (.-scoreDocs top-docs)))
      (finally
        (.release smgr searcher)))))
