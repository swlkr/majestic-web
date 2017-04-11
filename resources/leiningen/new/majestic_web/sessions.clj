(ns {{name}}.logic.sessions
  (:require [buddy.hashers :as hashers]
            [{{name}}.utils :refer [flip]]
            [{{name}}.env :as env]
            [{{name}}.logic.users :as users]))

(defn correct-password? [req-pass db-pass]
  (hashers/check req-pass db-pass))

(defn valid-user? [user req-user]
  (and
    ((comp not nil?) user)
    (correct-password? (:password req-user) (:password user))))

(defn create! [{:keys [params]}]
  (let [{:keys [email password]} params
        user (users/get-users-by-email {:email email} {:result-set-fn first})]
    (if (valid-user? user params)
      [(select-keys user [:id :email]) nil]
      [nil "Invalid email or password"])))
