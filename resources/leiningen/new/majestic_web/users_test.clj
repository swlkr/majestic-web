(ns {{name}}.logic.users-test
  (:require [clojure.test :refer :all]
            [{{name}}.logic.users :refer :all]))

(deftest users-test
  (testing "body->db"
    (let [db-params (body->db {:email "hello" :password "hello"})]
      (is (= true (every? db-params [:email :password :id]))))))
