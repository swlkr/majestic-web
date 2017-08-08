(ns {{name}}.users.http
  (:require [{{name}}.users.html :as users.html]
            [{{name}}.users.logic :as users.logic]
            [{{name}}.responses :as res]
            [{{name}}.utils :as utils]))

(defn form [req]
  (res/ok (users.html/form req)))

(defn create [req]
  (let [params (:params req)
        session (:session req)
        [user error] (utils/try! (users.logic/create! params))
        identity (select-keys user [:id :email])]
    (if (nil? error)
      (-> (res/redirect "/")
          (assoc :session (assoc session :identity identity)))
      (form (assoc req :error error)))))
