(ns person-data-processor.db
  (:require [mount.core :refer [defstate]]
            [conman.core :as conman]))

(def pool-spec
  {:jdbc-url "jdbc:h2:mem:"})

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

(defn get-records
  "Get all records, optionally sort.

  Supported sort-by keywords:
    :email-and-last-name - Sort by email descending, last name ascending
    :date-of-birth - Sort by date of birth ascending
    :last-name - Sort by last name descending"
  ([] (get-records nil))
  ([sort-by]
   (->> (select-records {:sort-by sort-by})
        (map #(dissoc % :id))
        (into []))))