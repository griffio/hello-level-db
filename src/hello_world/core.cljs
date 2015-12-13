(ns ^:figwheel-always hello-world.core
  (:require-macros [cljs.core.async.macros :refer [go]] [util.macros :refer [<?]])
  (:require [cljs.nodejs :as nodejs]
            [cljs.core.async :refer [<! >! put! close! chan]]
            [util.error :refer [log-error]]))
(nodejs/enable-util-print!)
(def level (nodejs/require "level"))
(def path (nodejs/require "path"))
(def location-to-db (.join path (.cwd nodejs/process) "test_db"))
(println location-to-db)
(defonce db (level location-to-db #js {:createIfMissing true :compression true :keyEncoding "utf8" :valueEncoding "utf8"}))
(defn level-is-open? [] (let [out (chan)] (put! out (.isOpen db)) out))
(defn level-close [] (let [out (chan)] (.close db (fn [er] (if er (put! out er) (put! out true)))) out))
(defn level-put [key value] (let [out (chan)] (.put db key value (fn [er] (if er (put! out er) (put! out true)))) out))
(defn level-get [key] (let [out (chan)] (.get db key (fn [er value] (if er (put! out er) (put! out value)))) out))

(defn level-batch [ops]
  (let [out (chan)]
    (.batch db ops (fn [er]
                     (if er (put! out er) (put! out true))))
    out))

(defn level-read [start end]
  (let [out (chan) stream (.createReadStream db #js {:gte start :lte end}) result []]
    (.on stream "data" (fn [data]
                         (put! out data)))
    (.on stream "end" (fn []
                        (close! out)))
    out))

(def ops (array #js {:type "put" :key "age" :value 800}
                #js {:type "put" :key "location" :value "Dagobah"}
                #js {:type "put" :key "person" :value "Yoda"}
                #js {:type "put" :key "skill" :value "Jedi Master"}))

(defn -main [& args]
  (let [data-chan (level-read \a \z)]
    (go (try
          (<? (level-put "age" 42))
          (<? (level-put "location" "Costa Mesa"))
          (<? (level-put "person" "Bobert"))
          (<? (level-put "skill" "I know computer"))
          (let [age (<? (level-get "age"))
                location (<? (level-get "location"))
                person (<? (level-get "person"))
                skill (<? (level-get "skill"))]
            (println (str "age:" age ",location:" location ",person:" person ",skill:" skill)))
          (<? (level-batch ops))
          (loop []
            (let [data (<? data-chan)]
              (when data (println data) (recur))))
          (<? (level-get "bogus"))
          (catch js/Error er
            (log-error er))))))

(set! *main-cli-fn* -main)