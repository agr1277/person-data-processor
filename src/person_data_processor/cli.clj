(ns person-data-processor.cli
  (:require [mount.core :as m]
            [person-data-processor.parse :as parse]
            [person-data-processor.db :as db]
            [java-time :as jt])
  (:gen-class))

(defn- date->date-str [date]
  (jt/format "MM/dd/yyyy" (jt/local-date date)))

(defn -main
  [sort-by & args]
  (m/start)
  (doseq [record (parse/input-files->records args)]
    (db/insert-record! record))
  (clojure.pprint/print-table (->> (db/get-records (keyword sort-by))
                                   (map #(update % :date_of_birth date->date-str))))
  (m/stop))