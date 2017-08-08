(ns {{name}}.server-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [{{name}}.server :refer [create-app]]))

(deftest test-app
  (let [app (create-app)]
    (testing "main route"
      (let [response (app (mock/request :get "/"))]
        (is (= (:status response) 302))
        (is (= "http://localhost/login" (get-in response [:headers "Location"])))
        (is (= "/login" (get-in response [:headers "Turbolinks-Location"])))))

    (testing "not-found route"
      (let [response (app (mock/request :get "/not-found"))]
        (is (= (:status response) 404))))))
