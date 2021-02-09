(ns person-data-processor.db-test
  (:require [clojure.test :refer :all]
            [person-data-processor.db :as db]
            [mount.core :refer [start stop]]
            [java-time :as jt]))

(defn- db-fixture [f]
  (start)
  (db/create-records-table!)
  (f)
  (stop))

(defn- test-data [f]
  (let [records [{:last-name      "Youngest"
                  :first-name     "Chris"
                  :email          "chris@example.com"
                  :favorite-color "blue"
                  :date-of-birth  (jt/local-date 1994 05 13)}
                 {:last-name      "Oldest"
                  :first-name     "Steve"
                  :email          "steve@example.com"
                  :favorite-color "red"
                  :date-of-birth  (jt/local-date 1990 05 20)}
                 {:last-name      "Middle"
                  :first-name     "Chris"
                  :email          "chris@example.com"
                  :favorite-color "green"
                  :date-of-birth  (jt/local-date 1993 05 20)}]]
    (doseq [record records]
      (db/insert-record! record)))
  (f))

(use-fixtures :each db-fixture test-data)

(deftest test-queries
  (testing "can select inserted rows"
    (let [expected-records [{:last_name      "Youngest"
                             :first_name     "Chris"
                             :email          "chris@example.com"
                             :favorite_color "blue"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1994 05 13))}
                            {:last_name      "Oldest"
                             :first_name     "Steve"
                             :email          "steve@example.com"
                             :favorite_color "red"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1990 05 20))}
                            {:last_name      "Middle"
                             :first_name     "Chris"
                             :email          "chris@example.com"
                             :favorite_color "green"
                             :date_of_birth  (jt/java-date (jt/zoned-date-time 1993 05 20))}]]
      (is (= expected-records (db/get-records)))))
  (testing "can select rows sorted by email descending, and last name ascending"
    (let [records (->> (db/get-records :email-and-last-name)
                       (map #(vec [(:email %) (:last_name %)])))]
      (is (= [["steve@example.com" "Oldest"]
              ["chris@example.com" "Middle"]
              ["chris@example.com" "Youngest"]] records))))
  (testing "can select rows sorted by date-of-birth"
    (let [records (->> (db/get-records :date-of-birth)
                       (map #(vec [(:last_name %) (:first_name %)])))]
      (is (= [["Oldest" "Steve"]
              ["Middle" "Chris"]
              ["Youngest" "Chris"]] records))))
  (testing "can select rows sorted by last-name"
    (let [records (->> (db/get-records :last-name)
                       (map #(vec [(:last_name %) (:first_name %)])))]
      (is (= [["Youngest" "Chris"]
              ["Oldest" "Steve"]
              ["Middle" "Chris"]] records)))))