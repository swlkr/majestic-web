(ns {{name}}.routes
  (:require [compojure.core :refer [defroutes GET POST wrap-routes]]
            [compojure.route :refer [not-found]]
            [{{name}}.routes.login :as login]
            [{{name}}.routes.signup :as signup]
            [{{name}}.routes.logout :as logout]
            [{{name}}.routes.home :as home]
            [{{name}}.components :refer [not-found-page]]
            [{{name}}.middleware :refer [wrap-auth]]))

(defroutes auth-routes
  (GET "/" [] home/get)
  (GET "/logout" [] logout/get))

(defroutes routes
  (GET "/login" [] login/get)
  (POST "/login" [] login/post)
  (GET "/signup" [] signup/get)
  (POST "/signup" [] signup/post)
  (-> auth-routes
      (wrap-routes wrap-auth))
  (not-found (not-found-page)))
