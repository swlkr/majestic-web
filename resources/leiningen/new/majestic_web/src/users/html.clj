(ns {{name}}.users.html
  (:require [{{name}}.html :as html]))

(defn form [req]
  (let [{:keys [error params]} req
        {:keys [email password confirm-password]} params]
    (html/layout
      (html/center
        (html/form {:method "post" :action "/signup"}
         [:div {:class "w5"}
           (html/info error)
           (html/input {:type "text"
                        :name "email"
                        :value email
                        :placeholder "you@gmail.com"})
           (html/input {:type "password"
                        :name "password"
                        :value password
                        :placeholder "**********"})
           (html/input {:type "password"
                        :name "confirm-password"
                        :value confirm-password
                        :placeholder "**********"})
           (html/submit "Sign Up")])
        (html/link "/login" "Already have an account?")))))
