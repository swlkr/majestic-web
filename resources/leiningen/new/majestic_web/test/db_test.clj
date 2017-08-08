(ns {{name}}.db-test
  (:require [clojure.test :refer :all]
            [{{name}}.db :refer :all]
            [{{name}}.utils :refer [now]]))

(deftest test-fmt-jdbc
  (testing "a heroku connection string"
    (let [heroku "postgres://username:password@host.compute-1.amazonaws.com:5432/db_name"
          jdbc "jdbc:postgresql://host.compute-1.amazonaws.com:5432/db_name?user=username&password=password"]
      (is (= jdbc (fmt-jdbc heroku)))))

  (testing "get-user-and-pass a heroku connection string "
    (let [heroku "postgres://username:password@host.compute-1.amazonaws.com:5432/db_name"]
      (is (= ["username" "password"] (get-user-and-pass heroku)))))

  (testing "a regular connection string"
    (is (= "jdbc:postgresql://localhost:5432/db_name" (fmt-jdbc "postgresql://localhost:5432/db_name")))))
