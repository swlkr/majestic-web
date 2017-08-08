(ns {{name}}.db
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [environ.core :refer [env]]))

(defn get-user-and-pass [str]
  (let [val (-> str
                (string/split #"//")
                second
                (string/split #"@")
                first
                (string/split #":"))]
    [(first val) (second val)]))

(defn fmt-jdbc [conn-str]
  (if (and
        (not (nil? conn-str))
        (= 3 (->> conn-str (re-seq #":") count)))
    (let [[user password] (get-user-and-pass conn-str)
          p-conn-str (-> conn-str
                         (string/split #"@")
                         second)]
      (str "jdbc:postgresql://" p-conn-str "?user=" user "&password=" password))
    (str "jdbc:" conn-str)))

(defonce conn {:connection-uri (fmt-jdbc (env :database-url))})

(defn yesql-conn []
  {:connection conn})
