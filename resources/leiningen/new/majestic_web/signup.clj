(ns {{name}}.routes.signup
  (:require [{{name}}.components :refer [layout center link form input submit label info]]
            [{{name}}.logic.users :as users]
            [{{name}}.responses :refer [redirect]])
  (:refer-clojure :exclude [get]))

(defn get [{:keys [error params] :as req}]
  (let [{:keys [email password confirm-password]} params
        err (if (nil? error) nil (info error))]
    (layout
      (center
        (form {:method "post" :action "/signup"}
              err
              (input {:type "text"
                      :name "email"
                      :value email
                      :placeholder "you@gmail.com"})
              (input {:type "password"
                      :name "password"
                      :value password
                      :placeholder "**********"})
              (input {:type "password"
                      :name "confirm-password"
                      :value confirm-password
                      :placeholder "**********"})
              (submit "Sign Up"))
        (link "/login" "Already have an account?")))))

(defn post [{:keys [session] :as req}]
  (let [[user error] (users/create! req)]
    (if (nil? error)
      (-> (redirect "/")
          (assoc :session (assoc session :identity user)))
      (get (assoc req :error error)))))
