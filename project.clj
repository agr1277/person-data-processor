(defproject person-data-processor "0.1.0-SNAPSHOT"
  :license {:name "MIT License"
            :url "https://spdx.org/licenses/MIT.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clojure.java-time "0.3.2"]
                 [mount "0.1.16"]
                 [conman "0.9.1"]
                 [com.h2database/h2 "1.4.200"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ch.qos.logback/logback-classic "1.1.3"]]
  :main ^:skip-aot person-data-processor.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
