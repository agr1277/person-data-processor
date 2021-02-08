(ns person-data-processor.core
  (:require [clojure.string :as s]
            [java-time :as jt])
  (:gen-class))

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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
