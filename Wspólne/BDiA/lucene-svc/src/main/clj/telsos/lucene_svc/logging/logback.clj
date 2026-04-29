(ns telsos.lucene-svc.logging.logback
  (:import
   (ch.qos.logback.classic Logger LoggerContext)
   (ch.qos.logback.classic.encoder PatternLayoutEncoder)
   (ch.qos.logback.core Appender)
   (ch.qos.logback.core.rolling RollingFileAppender TimeBasedRollingPolicy)
   (org.slf4j LoggerFactory)))

(set! *warn-on-reflection*       true)
(set! *unchecked-math* :warn-on-boxed)

(defn configure-FILE-appender!
  [log-name]
  (let [log-name (name log-name)
        context  ^LoggerContext (LoggerFactory/getILoggerFactory)
        root     (LoggerContext/.getLogger context Logger/ROOT_LOGGER_NAME)

        encoder
        (doto (PatternLayoutEncoder.)
          (PatternLayoutEncoder/.setContext context)
          (PatternLayoutEncoder/.setPattern "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n")
          (PatternLayoutEncoder/.start))

        rolling-policy
        (doto (TimeBasedRollingPolicy.)
          (TimeBasedRollingPolicy/.setContext context)
          (TimeBasedRollingPolicy/.setFileNamePattern (format "logs/application-%s.%%d{yyyy-MM-dd}.log" log-name))
          (TimeBasedRollingPolicy/.setMaxHistory 30))

        file-appender
        (doto (RollingFileAppender.)
          (RollingFileAppender/.setContext context)
          (RollingFileAppender/.setName "FILE")
          (RollingFileAppender/.setFile (format "logs/application-%s.log" log-name))
          (RollingFileAppender/.setEncoder encoder)
          (RollingFileAppender/.setRollingPolicy rolling-policy))]

    (TimeBasedRollingPolicy/.setParent rolling-policy file-appender)
    (TimeBasedRollingPolicy/.start rolling-policy)
    (RollingFileAppender/.start file-appender)

    (let [it (Logger/.iteratorForAppenders root)]
      (while (java.util.Iterator/.hasNext it)
        (let [app ^Appender (java.util.Iterator/.next it)]
          (when (= (Appender/.getName app) "FILE")
            (Logger/.detachAppender root app)))))

    (Logger/.addAppender root file-appender)))
