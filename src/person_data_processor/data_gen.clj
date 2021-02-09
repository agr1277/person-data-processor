(ns person-data-processor.data-gen
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure.math.combinatorics :refer [cartesian-product]]
            [clojure.data.generators :as gen]
            [java-time :as jt]))

(defn load-names [path]
  (with-open [rdr (io/reader path)]
    (->> rdr
         line-seq
         (map s/upper-case)
         (into []))))

(defn generate-names []
  (let [first-names (load-names "resources/first_names.txt")
        last-names (shuffle (load-names "resources/last_names.txt"))]
    (cartesian-product first-names last-names)))

(defn generate-random-iso-8601-date []
  (jt/format "yyyy-MM-dd" (jt/zoned-date-time (jt/instant (gen/date)) (jt/zoned-date-time))))

(defn generate-test-data []
  (let [colors ["red" "green" "blue" "yellow" "purple" "orange"]
        first-and-last-names (generate-names)]
    (for [[first-name last-name] first-and-last-names]
      [last-name first-name (str (s/lower-case first-name) "@example.com") (rand-nth colors) (generate-random-iso-8601-date)])))

(defn write-test-rows [file-name separator rows]
  (with-open [writer (io/writer (str file-name))]
    (doseq [row rows]
      (.write writer (str (s/join separator row) "\n"))))
  (println (count rows) " rows written to " file-name))

(defn write-test-files [output-file-prefix filesize]
  (write-test-rows (str output-file-prefix "-pipe.csv") " | " (take filesize (generate-test-data)))
  (write-test-rows (str output-file-prefix "-comma.csv") ", " (take filesize (generate-test-data)))
  (write-test-rows (str output-file-prefix "-space.csv") " " (take filesize (generate-test-data))))

(defn -main [output-file-prefix filesize & args]
  (write-test-files output-file-prefix (java.lang.Integer/parseInt filesize)))