(ns person-data-processor.cli-test
  (:require [clojure.test :refer :all]
            [person-data-processor.cli :as cli]))

(deftest test-cli-e2e
  (testing "cli processes input file and writes output to stdout"
    (let [out-str (clojure.string/split (with-out-str (cli/-main "last-name" "test/test_input.csv")) #"\n")]
      ; 3 leader rows + 3 output rows
      (is (= 6 (count out-str)))) ))