(ns {{name}}.users.logic
  (:require [buddy.hashers :as hashers]
            [{{name}}.users.db :as users.db]
            [{{name}}.utils :as utils]))

(defn email? [str]
  (and
    (string? str)
    (not (nil? (re-find #".+@.+\." str)))))

(defn decent-password? [password]
  (let [min-length 12]
    (and
      (string? password)
      (>= (count password) min-length))))

(defn validate-email [{:keys [email] :as user}]
  (if (email? email)
    user
    (throw (Exception. "That's not an email"))))

(defn validate-password [{:keys [password] :as user}]
  (if (decent-password? password)
    user
    (throw (Exception. "Passwords need to be at least 12 characters"))))

(defn validate-matching-passwords [{:keys [password confirm-password] :as user}]
  (if (= password confirm-password)
    user
    (throw (Exception. "Passwords need to match"))))

(defn validate [{:keys [email password confirm-password] :as user}]
  (-> user
      validate-email
      validate-password
      validate-matching-passwords))

(defn encrypt-password [user]
  (let [encrypted-password (hashers/derive (:password user))]
    (assoc user :password encrypted-password)))

(defn db-params [user]
  {:id (or (:id user) (utils/uuid))
   :email (:email user)
   :password (:password user)})

(defn create! [user]
  (-> user
      validate
      encrypt-password
      db-params
      users.db/insert<!))
