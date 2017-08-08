(ns {{name}}.sessions.http
  (:require [{{name}}.sessions.html :as sessions.html]
            [{{name}}.sessions.logic :as sessions.logic]
            [{{name}}.responses :as res]))

(defn form [req]
  (res/ok (sessions.html/form req)))

(defn create [req]
  (let [session (:session req)
        params (:params req)
        [identity error] (sessions.logic/create! params)]
    (if (not (nil? error))
      (form (assoc req :error error))
      (-> (res/redirect "/")
          (assoc :session (assoc session :identity identity))))))

(defn delete [req]
  (-> (res/redirect "/login")
      (assoc :session {})))
