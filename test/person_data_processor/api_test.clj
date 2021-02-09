(ns person-data-processor.api-test
  (:require [clojure.test :refer :all]
            [person-data-processor.api :as api :refer [app-routes]]
            [clojure.data.json :as json]))

(defn- test-body [str]
  (clojure.java.io/input-stream (java.io.ByteArrayInputStream. (.getBytes str))))

(defn- start-db [f]
  (mount.core/start)
  (f)
  (mount.core/stop))

(use-fixtures :each start-db)

(deftest test-api-e2e
  (do
    (app-routes {:request-method :post :uri "/records" :body (test-body "Sample John john@example.com green 1990-07-19")})
    (app-routes {:request-method :post :uri "/records" :body (test-body "Example | Chris | chris@example.com | blue | 1992-05-13")})
    (app-routes {:request-method :post :uri "/records" :body (test-body "Example, Steve, steve@example.com, red, 1990-04-19")}))
  (testing "api functions end to end"
    (doseq [uri ["email" "birthdate" "name"]]
      (let [res (app-routes {:request-method :get :uri (str "/records/" uri)})]
        (is (= 200 (:status res)))
        (is (= 3 (count (json/read-str (:body res)))))))))
