(ns {{name}}.server
  (:require [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.backends.session :refer [session-backend]]
            [clojure.tools.namespace.repl :as tn]
            [org.httpkit.server :as http]
            [ring.middleware.session.cookie :refer [cookie-store]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]
            [{{name}}.middleware :refer [wrap-exceptions unauthorized-handler wrap-force-ssl]]
            [{{name}}.env :as env]
            [{{name}}.routes :as routes]
            [{{name}}.utils :as utils])
  (:refer-clojure :exclude [run!]))

(defn create-app []
  (let [auth-backend (session-backend {:unauthorized-handler unauthorized-handler})]
    (-> routes/routes
        (wrap-authentication auth-backend)
        (wrap-authorization auth-backend)
        (wrap-exceptions)
        (wrap-resource "public")
        (wrap-defaults (-> site-defaults
                           (assoc-in [:session :cookie-attrs :max-age] 86400)
                           (assoc-in [:session :store] (cookie-store {:key env/secret})))))))

(defonce server (atom nil))

(defn start []
  (let [opts {:port env/port}
        _ (utils/add-json-to-yesql)
        app (create-app)]
    (reset! server (http/run-server (wrap-reload app) opts))
    (println (str "Listening on port " (:port opts)))))

(defn stop []
  (when @server
    (@server :timeout 100)
    (reset! server nil)))

(defn restart []
  (stop)
  (tn/refresh :after '{{name}}.core/start))

(defn run! [port]
  (let [opts {:port (or port env/port)}
        _ (utils/add-json-to-yesql)]
    (http/run-server (wrap-force-ssl (create-app)) opts)
    (println (str "Listening on port " (:port opts)))))

