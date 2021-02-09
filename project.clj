(defproject person-data-processor "0.1.0-SNAPSHOT"
  :license {:name "MIT License"
            :url  "https://spdx.org/licenses/MIT.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clojure.java-time "0.3.2"]
                 [mount "0.1.16"]
                 [conman "0.9.1"]
                 [com.h2database/h2 "1.4.200"]
                 [org.clojure/tools.logging "1.1.0"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [org.clojure/math.combinatorics "0.1.6"]
                 [org.clojure/data.generators "1.0.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.6.2"]
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]]
  :aliases {"generate-data" ["run" "-m" "person-data-processor.data-gen"]
            "cli"           ["run" "-m" "person-data-processor.cli"]
            "api"           ["run" "-m" "person-data-processor.api"]}
  :target-path "target/%s")
