(ns {{name}}.routes.logout
  (:require [{{name}}.responses :refer [redirect]])
  (:refer-clojure :exclude [get]))

(defn get [req]
  (-> (redirect "/login")
      (assoc :session {})))
