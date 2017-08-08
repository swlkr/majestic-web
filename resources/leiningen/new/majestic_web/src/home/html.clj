(ns {{name}}.home.html
  (:require [{{name}}.html :as html]))

(defn index [req]
  (html/layout
    (html/center
      "home page"
      (html/link "/logout" "Logout"))))

