(defproject hello-world "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.36" :classifier "aot" :exclusion [org.clojure/data.json]]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]
                 [org.clojure/core.async "0.2.374"]]
  :jvm-opts ^:replace ["-Xms256m"]
  :plugins [[lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]
  :npm {:dependencies [[source-map-support "0.3.2"]
                       [level "1.4.0"]
                       [ws "0.8.1"]]}
  :clean-targets ^{:protect false} ["out"]
  :main "hello-world.core"
  :source-paths ["src"]
  :cljsbuild {
              :builds [{:id           "dev"
                        :source-paths ["src"]
                        :figwheel     {}
                        :compiler     {:asset-path           "out"
                                       :main                 hello-world.core
                                       :optimizations        :none
                                       :static-fns           true
                                       :output-dir           "out"
                                       :output-to            "main.js"
                                       :source-map-timestamp true
                                       :target               :nodejs}}]}
 )