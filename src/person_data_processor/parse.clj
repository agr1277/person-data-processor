(ns person-data-processor.parse
  (:require [clojure.string :as s]
            [java-time :as jt]
            [clojure.java.io :as io]))

(defn input-row->record [row]
  (let [row (->> (s/split row #" \| |, | ")
                 (map s/trim)
                 (into []))
        [last-name first-name email favorite-color date-of-birth] row]
    {:last-name      last-name
     :first-name     first-name
     :email          email
     :favorite-color favorite-color
     :date-of-birth  (jt/local-date "MM/dd/yyyy" date-of-birth)}))

(defn input-rows->records [rows]
  (->> rows
       (map input-row->record)))

(defn input-file->records [path]
  (with-open [rdr (io/reader path)]
    (->> rdr
         line-seq
         (into [])
         input-rows->records)))

(defn input-files->records [paths]
  (->> paths
       (map input-file->records)
       flatten))
