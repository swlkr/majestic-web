(ns {{name}}.sessions.logic-test
  (:require [clojure.test :refer :all]
            [buddy.hashers :as hashers]
            [{{name}}.sessions.logic :as sessions.logic]
            [{{name}}.users.logic :as users.logic]
            [{{name}}.server :as server]))

(deftest valid-user?-test
  (let [db-user {:id "uuid" :email "test@test.com" :password (hashers/derive "password123456")}
        app (server/create-app)]

    (testing "with nils"
      (let [result (sessions.logic/valid-user? nil nil)]
        (is (= false result))))

    (testing "with a valid user"
      (let [result (sessions.logic/valid-user? db-user {:email "test@test.com" :password "password123456"})]
        (is (= true result))))))
