(defproject person-data-processor "0.1.0-SNAPSHOT"
  :license {:name "MIT License"
            :url "https://spdx.org/licenses/MIT.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot person-data-processor.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
