(ns {{name}}.sessions.html
  (:require [{{name}}.html :as html]))

(defn form [req]
  (let [{:keys [error params]} req
        {:keys [email password]} params]
    (html/layout
      (html/center
        (html/form {:method "post" :action "/login"}
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
            (html/submit "Login")])
        (html/link "/signup" "Don't have an account?")))))
