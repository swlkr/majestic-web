(ns {{name}}.http
  (:require [compojure.core :refer [defroutes GET POST wrap-routes]]
            [compojure.route :as compojure.route]
            [{{name}}.users.http :as users.http]
            [{{name}}.sessions.http :as sessions.http]
            [{{name}}.home.http :as home.http]
            [{{name}}.html :as html]
            [{{name}}.middleware :as middleware]))

(defroutes auth
  (GET "/" [] home.http/index)
  (GET "/logout" [] sessions.http/delete))

(defroutes all
  (GET "/login" [] sessions.http/form)
  (POST "/login" [] sessions.http/create)
  (GET "/signup" [] users.http/form)
  (POST "/signup" [] users.http/create)
  (-> auth
      (wrap-routes middleware/wrap-auth))
  (compojure.route/not-found (html/not-found-page)))

