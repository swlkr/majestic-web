(ns {{name}}.routes.login
  (:require [{{name}}.components :refer [layout center link form input submit label info]]
            [{{name}}.logic.sessions :as sessions]
            [{{name}}.responses :refer [redirect]])
  (:refer-clojure :exclude [get]))

(defn get [{:keys [error params]}]
  (let [{:keys [email password]} params
        err (if (nil? error) nil (info error))]
    (layout
      (center
        (form {:method "post" :action "/login"}
              err
              (input {:type "text"
                      :name "email"
                      :value email
                      :placeholder "you@gmail.com"})
              (input {:type "password"
                      :name "password"
                      :value password
                      :placeholder "**********"})
              (submit "Login"))
        (link "/signup" "Don't have an account?")))))

(defn post [{:keys [session] :as req}]
  (let [[identity error] (sessions/create! req)]
    (if (not (nil? error))
      (get (assoc req :error error))
      (-> (redirect "/")
          (assoc :session (assoc session :identity identity))))))

