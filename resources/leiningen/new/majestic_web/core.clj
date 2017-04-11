(ns {{name}}.core
  (:require [{{name}}.server :as server]
            [{{name}}.env :as env]
            [{{name}}.utils :as utils])
  (:gen-class))

(def start server/start)
(def stop server/stop)
(def restart server/restart)

(defn -main [& [port]]
  (server/run! port))
