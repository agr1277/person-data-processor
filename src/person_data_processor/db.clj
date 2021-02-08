(ns person-data-processor.db
  (:require [mount.core :refer [defstate]]
            [conman.core :as conman]))

(def pool-spec
  {:jdbc-url "jdbc:h2:mem:"})

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

