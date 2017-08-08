(ns {{name}}.middleware
  (:require [buddy.auth :as buddy.auth]
            [{{name}}.responses :as res]
            [{{name}}.html :as html]))

(defn wrap-auth [handler]
  (fn [request]
    (if (buddy.auth/authenticated? request)
      (handler request)
      (buddy.auth/throw-unauthorized))))

(defn wrap-exceptions [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (res/server-error (html/error-page e))))))

(defn unauthorized-handler [req metadata]
  (if (buddy.auth/authenticated? req)
    (res/forbidden (html/forbidden-page))
    (res/redirect "/login")))

(defn wrap-force-ssl [handler]
  (fn [req]
    (let [headers (:headers req)
          host    (headers "host")]
      (if (or (= :https (:scheme req))
              (= "https" (headers "x-forwarded-proto")))
        (handler req)
        (-> (res/redirect (str "https://" host (:uri req)))
            (assoc :status 301))))))

