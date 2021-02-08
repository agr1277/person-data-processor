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

(defn- test-data [f]
  (let [records [{:last-name      "Example"
                  :first-name     "Chris"
                  :email          "chris@example.com"
                  :favorite-color "blue"
                  :date-of-birth  (jt/local-date 1991 05 13)}
                 {:last-name      "Example"
                  :first-name     "Steve"
                  :email          "steve@example.com"
                  :favorite-color "red"
                  :date-of-birth  (jt/local-date 1990 05 20)}
                 {:last-name      "Sample"
                  :first-name     "Chris"
                  :email          "chris@example.com"
                  :favorite-color "green"
                  :date-of-birth  (jt/local-date 1993 05 20)}]]
    (doseq [record records]
      (db/insert-record record)))
  (f))

(use-fixtures :each db-fixture test-data)

(deftest test-queries
  (testing "can select inserted rows"
    (let [expected-records [{:last_name      "Example"
                             :first_name     "Chris"
                             :email          "chris@example.com"
                             :favorite_color "blue"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1991 05 13))}
                            {:last_name      "Example"
                             :first_name     "Steve"
                             :email          "steve@example.com"
                             :favorite_color "red"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1990 05 20))}
                            {:last_name      "Sample"
                             :first_name     "Chris"
                             :email          "chris@example.com"
                             :favorite_color "green"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1993 05 20))}]]
      (is (= expected-records (db/get-records)))))
  (testing "can select rows sorted by email descending, and last name ascending"
    (let [records (db/get-records :email-and-last-name)]
      (is (= "steve@example.com" (:email (records 0))))
      (is (= ["chris@example.com" "Example"] [(:email (records 1)) (:last_name (records 1))]))
      (is (= ["chris@example.com" "Sample"] [(:email (records 2)) (:last_name (records 2))])))))