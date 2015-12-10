(defproject hello-world "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145" :classifier "aot"
                  :exclusion [org.clojure/data.json]]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]
                 [figwheel-sidecar "0.5.0-SNAPSHOT"]]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.1"]
            [lein-cljsbuild "1.1.1"]]
  :npm {:dependencies [[source-map-support "0.3.2"]
                       [level "1.4.0"]
                       [ws "0.8.1"]]}
  :clean-targets ^{:protect false} ["target"]
  :main "main.js"
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
  :figwheel {})