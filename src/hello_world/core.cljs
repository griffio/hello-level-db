(ns ^:figwheel-always hello-world.core
  (:require-macros
    [cljs.core.async.macros :refer [go]]
    [util.macros :refer [<?]])
  (:require
    [cljs.nodejs :as nodejs]
    [cljs.core.async :refer [<! put! chan]]
    [util.error :refer [log-error]]))

(nodejs/enable-util-print!)

(def level (nodejs/require "level"))

(def db (level "./test_db"))

(defn level-is-open? []
  (let [out (chan)]
    (put! out (.isOpen db))
    out))

(defn level-close []
  (let [out (chan)]
    (.close db (fn [er]
                 (if er (put! out er) (put! out true))))
    out))

(defn level-put [key value]
  (let [out (chan)]
    (.put db key value (fn [er]
                         (if er (put! out er) (put! out value))))
    out))

(defn level-get [key]
  (let [out (chan)]
    (.get db key (fn [er value]
                   (if er (put! out er) (put! out value))))
    out))

(defn -main [& args]

  (go (try
        (<? (level-put "age" 42))
        (<? (level-put "location" "Costa Mesa"))
        (<? (level-put "person" "Bobert"))
        (<? (level-put "skill" "I know computer"))
        (let [age (<? (level-get "age"))
              location (<? (level-get "location"))
              person (<? (level-get "person"))
              skill (<? (level-get "skill"))]
          (println (str "age:" age ",location:" location ",person:" person ",skill" skill)))
        (catch js/Error er
          (log-error er)))))

(set! *main-cli-fn* -main)