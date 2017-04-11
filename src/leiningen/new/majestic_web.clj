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
             [".gitignore" (render ".gitignore" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/db.clj" (render "db.clj" data)]
             ["test/{{sanitized}}/db_test.clj" (render "db_test.clj" data)]
             ["src/{{sanitized}}/env.clj" (render "env.clj" data)]
             ["resources/migrations/{{hstore-mig}}" (render "hstore-mig.edn" data)]
             ["src/{{sanitized}}/responses.clj" (render "responses.clj" data)]
             ["src/{{sanitized}}/components.clj" (render "components.clj" data)]
             ["src/{{sanitized}}/middleware.clj" (render "middleware.clj" data)]
             ["Procfile" (render "Procfile" data)]
             ["profiles.clj" (render "profiles.clj" data)]
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             ["src/{{sanitized}}/routes.clj" (render "routes.clj" data)]
             ["src/{{sanitized}}/routes/home.clj" (render "home.clj" data)]
             ["src/{{sanitized}}/routes/login.clj" (render "login.clj" data)]
             ["src/{{sanitized}}/routes/logout.clj" (render "logout.clj" data)]
             ["src/{{sanitized}}/routes/signup.clj" (render "signup.clj" data)]
             ["test/{{sanitized}}/server_test.clj" (render "server_test.clj" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["test/{{sanitized}}/logic/sessions_test.clj" (render "sessions_test.clj" data)]
             ["src/{{sanitized}}/logic/sessions.clj" (render "sessions.clj" data)]
             ["test/{{sanitized}}/logic/users_test.clj" (render "users_test.clj" data)]
             ["resources/migrations/{{users-mig}}" (render "users-mig.edn" data)]
             ["src/{{sanitized}}/logic/users.clj" (render "users.clj" data)]
             ["resources/sql/users.sql" (render "users.sql" data)]
             ["src/{{sanitized}}/utils.clj" (render "utils.clj" data)])))
