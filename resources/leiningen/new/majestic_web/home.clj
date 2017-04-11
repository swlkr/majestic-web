(ns {{name}}.routes.home
  (:require [{{name}}.components :refer [layout center link]])
  (:refer-clojure :exclude [get]))

(defn get [req]
  (layout
    (center
      "home page"
      (link "/logout" "Logout" {:data-turbolinks "false"}))))
