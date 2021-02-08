(ns person-data-processor.core-test
  (:require [clojure.test :refer :all]
            [person-data-processor.core :refer :all]
            [java-time :as jt]))

(deftest test-input-row->record
  (testing "create a record from a pipe delimited row"
    (is (= {:last-name      "Example"
            :first-name     "Chris"
            :email          "chris@example.com"
            :favorite-color "blue"
            :date-of-birth  (jt/local-date 1992 05 13)}
           (input-row->record "Example | Chris | chris@example.com | blue | 05/13/1992")))))
