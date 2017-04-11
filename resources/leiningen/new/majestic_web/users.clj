(ns {{name}}.logic.users
  (:require [yesql.core :as yesql]
            [buddy.hashers :as hashers]
            [{{name}}.db :as db]
            [{{name}}.utils :refer [uuid throw+ err->>]]))

(yesql/defqueries "sql/users.sql" (db/yesql-conn))

(defn email? [str]
  (and
    (string? str)
    (not (nil? (re-find #".+@.+\." str)))))

(defn decent-password? [password]
  (let [PASSWORD_LENGTH 12]
    (and
      (string? password)
      (> (count password) PASSWORD_LENGTH))))

(defn matching-passwords? [[password confirm-password]]
  (= password confirm-password))

(defn validate-email [{:keys [email] :as user}]
  (if (email? email)
    [user nil]
    [nil "That's not an email"]))

(defn validate-password [{:keys [password] :as user}]
  (if (decent-password? password)
    [user nil]
    [nil "Passwords need to be at least 12 characters"]))

(defn validate-matching-passwords [{:keys [password confirm-password] :as user}]
  (if (matching-passwords? [password confirm-password])
    [user nil]
    [nil "Passwords need to match"]))

(defn validate-user [{:keys [email password confirm-password] :as user}]
  (err->> user
          validate-email
          validate-password
          validate-matching-passwords))

(defn encrypt-password [password]
  (hashers/derive password))

(defn body->db [body]
  (let [ec (encrypt-password (:password body))]
    (assoc body :id (uuid) :password ec)))

(defn create! [{:keys [params]}]
  (let [[new-user err] (validate-user params)]
    (if (not (nil? err))
      [nil err]
      [(-> new-user
           body->db
           insert-user<!
           (select-keys [:id :email])) nil])))
