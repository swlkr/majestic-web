(ns {{name}}.sessions.logic
  (:require [buddy.hashers :as hashers]
            [{{name}}.users.db :as users.db]))

(defn correct-password? [req-pass db-pass]
  (hashers/check req-pass db-pass))

(defn valid-user? [user req-user]
  (and
    ((comp not nil?) user)
    (correct-password? (:password req-user) (:password user))))

(defn create! [params]
  (let [{:keys [email password]} params
        user (first (users.db/find-by-email {:email email}))]
    (if (valid-user? user params)
      [(select-keys user [:id :email]) nil]
      [nil "Invalid email or password"])))
