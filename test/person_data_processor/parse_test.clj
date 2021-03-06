(ns person-data-processor.parse-test
  (:require [clojure.test :refer :all]
            [person-data-processor.parse :refer :all]
            [java-time :as jt]))

(deftest test-input-row->record
  (let [expected-record {:last-name      "Example"
                         :first-name     "Chris"
                         :email          "chris@example.com"
                         :favorite-color "blue"
                         :date-of-birth  (jt/local-date 1992 05 13)}]
    (testing "parses pipe delimited row"
      (is (= expected-record
             (input-row->record "Example | Chris | chris@example.com | blue | 1992-05-13"))))
    (testing "parses comma delimited row"
      (is (= expected-record
             (input-row->record "Example, Chris, chris@example.com, blue, 1992-05-13"))))
    (testing "parses space delimited row"
      (is (= expected-record
             (input-row->record "Example Chris chris@example.com blue 1992-05-13"))))))

(deftest test-input-rows->records
  (testing "parses rows delimited by pipe, comma, or space"
    (is (= [{:last-name      "Example"
             :first-name     "Chris"
             :email          "chris@example.com"
             :favorite-color "blue"
             :date-of-birth  (jt/local-date 1992 05 13)}
            {:last-name      "Example"
             :first-name     "Steve"
             :email          "steve@example.com"
             :favorite-color "red"
             :date-of-birth  (jt/local-date 1990 04 19)}
            {:last-name      "Sample"
             :first-name     "John"
             :email          "john@example.com"
             :favorite-color "green"
             :date-of-birth  (jt/local-date 1990 07 19)}]
           (input-rows->records ["Example | Chris | chris@example.com | blue | 1992-05-13"
                                 "Example, Steve, steve@example.com, red, 1990-04-19"
                                 "Sample John john@example.com green 1990-07-19"])))))

(deftest test-input-file->records
  (testing "reads file and parses into records"
    (is (= [{:last-name      "Example"
             :first-name     "Chris"
             :email          "chris@example.com"
             :favorite-color "blue"
             :date-of-birth  (jt/local-date 1992 05 13)}
            {:last-name      "Example"
             :first-name     "Steve"
             :email          "steve@example.com"
             :favorite-color "red"
             :date-of-birth  (jt/local-date 1990 04 19)}
            {:last-name      "Sample"
             :first-name     "John"
             :email          "john@example.com"
             :favorite-color "green"
             :date-of-birth  (jt/local-date 1990 07 19)}]
           (input-file->records "test/test_input.csv")))))

(deftest test-input-files->records
  (testing "reads input files and parses into single list of records"
    (is (= 6 (count (input-files->records ["test/test_input.csv" "test/test_input.csv"]))))))