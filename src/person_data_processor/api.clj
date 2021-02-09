(ns person-data-processor.api
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.data.json :as json]
            [mount.core :as m]
            [person-data-processor.db :as db]
            [person-data-processor.parse :as parse])
  (:gen-class))

(defn get-records-response [sort-by]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (str (json/write-str (db/get-records sort-by)))})

(defroutes app-routes
           (POST "/records"
                 {body :body}
             :consumes ["text/plain"]
             (db/insert-record! (parse/input-row->record (slurp body)))
             (get-records-response :default))
           (GET "/records/email" []
             (get-records-response :email-and-last-name))
           (GET "/records/birthdate" []
             (get-records-response :date-of-birth))
           (GET "/records/name" []
             (get-records-response :last-name))
           (route/not-found "Unknown route"))

(extend-type java.sql.Date
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))

(defn -main
  [& args]
  (m/start)
  (server/run-server (wrap-defaults #'app-routes api-defaults) {:port 3000})
  (println (str "Running api at http://127.0.0.1:3000/")))