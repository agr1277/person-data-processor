(ns person-data-processor.cli
  (:require [mount.core :as m]
            [person-data-processor.parse :as parse]
            [person-data-processor.db :as db])
  (:gen-class))


(defn -main
  [sort-by & args]
  (m/start)
  (db/create-records-table!)
  (let [records (parse/input-files->records args)]
    (doseq [record records]
      (db/insert-record! record)))
  (clojure.pprint/print-table (db/get-records (keyword sort-by)))
  (m/stop))