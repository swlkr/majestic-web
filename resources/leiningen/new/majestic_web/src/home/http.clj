(ns {{name}}.home.http
  (:require [{{name}}.home.html :as html]
            [{{name}}.responses :as res]))

(defn index [req]
  (res/ok (html/index req)))

