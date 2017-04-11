(ns {{name}}.middleware
  (:require [buddy.auth :refer [authenticated? throw-unauthorized]]
            [{{name}}.responses :refer [server-error forbidden redirect]]
            [{{name}}.components :refer [error-page forbidden-page]]))

(defn wrap-auth [handler]
  (fn [request]
    (if (authenticated? request)
      (handler request)
      (throw-unauthorized))))

(defn wrap-exceptions [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (server-error (error-page e))))))

(defn unauthorized-handler [req metadata]
  (if (authenticated? req)
    (forbidden (forbidden-page))
    (redirect "/login")))

(defn wrap-force-ssl [handler]
  (fn [req]
    (let [headers (:headers req)
          host    (headers "host")]
      (if (or (= :https (:scheme req))
              (= "https" (headers "x-forwarded-proto")))
        (handler req)
        (-> (redirect (str "https://" host (:uri req)))
            (assoc :status 301))))))

