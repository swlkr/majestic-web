(ns {{name}}.users.db
  (:require [yesql.core :as yesql]
            [{{name}}.db :as db]))

(yesql/defqueries "sql/users.sql" (db/yesql-conn))
