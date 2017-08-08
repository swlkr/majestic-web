(ns leiningen.new.majestic-web
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main])
  (:import (java.text SimpleDateFormat)
           (java.util Date UUID)))

(def render (renderer "majestic_web"))

(def date-format "yyyyMMddHHmmss")

(def alphanumeric "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890")
(defn get-random-str [length]
  (apply str (repeatedly length #(rand-nth alphanumeric))))

(defn now []
  (.format (SimpleDateFormat. date-format) (new Date)))

(defn fmt-migration-name [name]
  (str (now) "_" (clojure.string/replace name #"\s+|-+|_+" "_") ".edn"))

(defn majestic-web
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)
              :hstore-mig (fmt-migration-name "add-hstore-ext")
              :users-mig (fmt-migration-name "create-users-table")
              :test-secret (get-random-str 16)
              :dev-secret (get-random-str 16)}]
    (main/info "Generating fresh 'lein new' majestic-web project.")
    (->files data

             ; root folder
             [".gitignore" (render ".gitignore" data)]
             ["Procfile" (render "Procfile" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]

             ; src folder
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/db.clj" (render "db.clj" data)]
             ["src/{{sanitized}}/responses.clj" (render "responses.clj" data)]
             ["src/{{sanitized}}/html.clj" (render "html.clj" data)]
             ["src/{{sanitized}}/middleware.clj" (render "middleware.clj" data)]
             ["src/{{sanitized}}/http.clj" (render "http.clj" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/utils.clj" (render "utils.clj" data)]
             ["src/{{sanitized}}/migrations.clj" (render "migrations.clj" data)]

             ; src/users folder
             ["src/{{sanitized}}/users/db.clj" (render "src/users/db.clj" data)]
             ["src/{{sanitized}}/users/html.clj" (render "src/users/html.clj" data)]
             ["src/{{sanitized}}/users/http.clj" (render "src/users/http.clj" data)]
             ["src/{{sanitized}}/users/logic.clj" (render "src/users/logic.clj" data)]

             ; src/home folder
             ["src/{{sanitized}}/home/html.clj" (render "src/home/html.clj" data)]
             ["src/{{sanitized}}/home/http.clj" (render "src/home/http.clj" data)]

             ; src/sessions folder
             ["src/{{sanitized}}/sessions/html.clj" (render "src/sessions/html.clj" data)]
             ["src/{{sanitized}}/sessions/http.clj" (render "src/sessions/http.clj" data)]
             ["src/{{sanitized}}/sessions/logic.clj" (render "src/sessions/logic.clj" data)]

             ; test folder
             ["test/{{sanitized}}/db_test.clj" (render "test/db_test.clj" data)]
             ["test/{{sanitized}}/server_test.clj" (render "test/server_test.clj" data)]
             ["test/{{sanitized}}/sessions/logic_test.clj" (render "test/sessions/logic_test.clj" data)]
             ["test/{{sanitized}}/users/logic_test.clj" (render "test/users/logic_test.clj" data)]

             ; resources folder
             ["resources/migrations/{{hstore-mig}}" (render "hstore-mig.edn" data)]
             ["resources/migrations/{{users-mig}}" (render "users-mig.edn" data)]
             ["resources/sql/users.sql" (render "users.sql" data)])))
