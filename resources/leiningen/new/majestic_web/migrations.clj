(ns {{name}}.migrations
  (:require [ragtime.jdbc :as jdbc]
            [ragtime.repl :as repl]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.java.jdbc :as java.jdbc]
            [environ.core :refer [env]]
            [{{name}}.db :as db]
            [{{name}}.utils :as utils])
  (:import (java.text SimpleDateFormat)
           (java.util Date)))

(defn fmt-date [date]
  (when (instance? Date date)
    (.format (SimpleDateFormat. "yyyyMMddHHmmss") date)))

(def migrations-dir
  "Default migrations directory"
  "resources/migrations/")

(def ragtime-format-edn
  "EDN template for SQL migrations"
  "{:up [\"\"]\n :down [\"\"]}")

(defn ragtime-conn []
  {:datastore  (jdbc/sql-database db/conn)
   :migrations (jdbc/load-resources "migrations")})

(defn migrate []
  (repl/migrate (ragtime-conn)))

(defn rollback []
  (repl/rollback (ragtime-conn)))

(defn migrations-dir-exist? []
  (.isDirectory (io/file migrations-dir)))

(defn migration-file-path [name]
  (when (and
          ((comp not nil?) name)
          ((comp not empty?) name)
          (string? name))
    (str migrations-dir (-> (utils/now) fmt-date) "_" (string/replace name #"\s+|-+|_+" "_") ".edn")))

(defn create [name]
  (let [migration-file (migration-file-path name)]
    (if-not (migrations-dir-exist?)
      (io/make-parents migration-file))
    (spit migration-file ragtime-format-edn)))

(defn get-cols [table]
  (let [sql ["select column_name from information_schema.columns where table_name = ?" table]]
    (java.jdbc/query db/conn sql)))

(defn in? [coll elm]
  (some #(= elm %) coll))

(defn update-cols? [col]
  (not (in? ["id" "created_at"] col)))

(def sql-dir "resources/sql/")

(defn sql-file-path [table]
  (str sql-dir table ".sql"))

(defn crud [table]
  (let [sql-file (sql-file-path table)
        rows (get-cols table)
        cols (map :column_name rows)
        update-cols (filter update-cols? cols)
        cols-string (string/join ", " cols)
        params (map #(str ":" %) cols)
        params-string (string/join ", " params)
        update-params (map #(str ":" %) update-cols)
        update-map (apply assoc {} (interleave update-cols update-params))
        updates (map #(str (first %) " = " (second %)) update-map)
        update-string (string/join ", " updates)
        insert (str "-- name: insert<!"
                    "\ninsert into " table " (" cols-string ") "
                    "\nvalues (" params-string ")")
        update (str "-- name: update<!"
                    "\nupdate " table
                    "\nset " update-string
                    "\nwhere id = :id")
        find (str
               "-- name: find-by-id"
               "\nselect *"
               "\nfrom " table
               "\nwhere id = :id")
        delete (str
                 "-- name: delete<!"
                 "\ndelete from " table
                 "\nwhere id = :id")
        contents (string/join "\n\n" [insert find update delete])
        _ (spit sql-file contents)])
  (println (str "Created resources/sql/" table ".sql")))
