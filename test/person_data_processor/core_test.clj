(ns person-data-processor.core-test
  (:require [clojure.test :refer :all]
            [person-data-processor.core :refer :all]
            [java-time :as jt]))

(deftest test-input-row->record
  (let [expected-record {:last-name      "Example"
                         :first-name     "Chris"
                         :email          "chris@example.com"
                         :favorite-color "blue"
                         :date-of-birth  (jt/local-date 1992 05 13)}]
    (testing "parses pipe delimited row"
      (is (= expected-record
             (input-row->record "Example | Chris | chris@example.com | blue | 05/13/1992"))))
    (testing "parses comma delimited row"
      (is (= expected-record
             (input-row->record "Example, Chris, chris@example.com, blue, 05/13/1992"))))
    (testing "parses space delimited row"
      (is (= expected-record
             (input-row->record "Example Chris chris@example.com blue 05/13/1992"))))))

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
           (input-rows->records ["Example | Chris | chris@example.com | blue | 05/13/1992"
                                 "Example, Steve, steve@example.com, red, 04/19/1990"
                                 "Sample John john@example.com green 07/19/1990"])))))
