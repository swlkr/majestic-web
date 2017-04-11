(ns {{name}}.logic.sessions-test
  (:require [clojure.test :refer :all]
            [{{name}}.logic.sessions :refer :all]
            [{{name}}.env :as env]
            [{{name}}.logic.users :as users]
            [{{name}}.server :refer [create-app]]))

(deftest tokens-test
  (let [db-user {:id "uuid" :email "test@test.com" :password (users/encrypt-password "password123456")}
        app (create-app)]
    (testing "valid-user? with nils"
      (let [result (valid-user? nil nil)]
        (is (= false result))))

    (testing "valid-user? with a valid user"
      (let [result (valid-user? db-user {:email "test@test.com" :password "password123456"})]
        (is (= true result))))))
