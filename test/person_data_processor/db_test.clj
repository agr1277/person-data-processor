(ns person-data-processor.db-test
  (:require [clojure.test :refer :all]
            [person-data-processor.db :as db]
            [mount.core :refer [start stop]]
            [java-time :as jt]))

(defn- db-fixture [f]
  (start)
  (db/create-records-table)
  (f)
  (stop))

(use-fixtures :once db-fixture)

(deftest test-db
  (testing "can select inserted rows"
    (do
      (db/insert-record {:last-name      "Example"
                         :first-name     "Chris"
                         :email          "chris@example.com"
                         :favorite-color "blue"
                         :date-of-birth  (jt/local-date 1992 05 13)})
      (db/insert-record {:last-name      "Example"
                         :first-name     "Steve"
                         :email          "steve@example.com"
                         :favorite-color "red"
                         :date-of-birth  (jt/local-date 1990 05 20)}))
    (let [records (->> (db/get-records)
                       (map #(dissoc % :id))
                       (into []))]
     (is (= [{:last_name      "Example"
              :first_name     "Chris"
              :email          "chris@example.com"
              :favorite_color "blue"
              :date_of_birth  (jt/java-date (jt/zoned-date-time 1992 05 13))}
             {:last_name      "Example"
              :first_name     "Steve"
              :email          "steve@example.com"
              :favorite_color "red"
              :date_of_birth  (jt/java-date (jt/zoned-date-time 1990 05 20))}] records)))))