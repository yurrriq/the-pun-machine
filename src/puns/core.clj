(ns puns.core
  "punXcore"
  {:author "Eric Bailey"}
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [puns.greyt :refer [pen-pun]]
            [puns.html :as html]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer :all])
  (:gen-class))

(defroutes ^:private app
  (GET "/" []
    (html/main-page))
  (POST "/" {{q :q} :params}
    (pen-pun q))
  (route/resources "/"))

(defonce ^:private server (atom nil))

(defn start-server
  "Starts the server on port 3000 or `$PORT`."
  []
  (let [port (or (some-> (System/getenv "PORT") bigint int) 3000)]
    (->> (run-server (wrap-defaults app site-defaults) {:port port})
         (reset! server))
    (println (format "Punning on http://localhost:%d" port))))

(defn stop-server
  "Stops the server after 100ms."
  []
  (when (some? @server)
    (@server :timeout 100)
    (reset! server nil)))

(defn -main
  "Calls [[start-server]]."
  [& args] (start-server))
