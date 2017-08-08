(ns {{name}}.users.logic-test
  (:require [clojure.test :refer :all]
            [{{name}}.users.logic :as users.logic]
            [{{name}}.users.db :as users.db]
            [{{name}}.utils :as utils]))

(deftest create!-test
  (testing "with valid params"
    (let [params {:id (utils/uuid) :email "test@test.com" :password "password123456" :confirm-password "password123456"}]
      (with-redefs [users.db/insert<! (fn [user] params)]
        (is (= params (users.logic/create! params))))))

  (testing "with invalid email"
    (let [params {:email nil}]
      (is (thrown-with-msg? Exception #"^That's not an email$" (users.logic/create! params)))))

  (testing "with invalid password"
    (let [params {:email "t@t.com" :password nil}]
      (is (thrown-with-msg? Exception #"^Passwords need to be at least 12 characters$" (users.logic/create! params)))))

  (testing "with invalid confirm password"
    (let [params {:email "t@t.com"
                  :password "password is 12 characters"
                  :confirm-password "hello"}]
      (is (thrown-with-msg? Exception #"^Passwords need to match$" (users.logic/create! params))))))
