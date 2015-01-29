(ns ^{:doc "punXcore"
      :author "Eric Bailey"}
  puns.core
  (:require [clojure.java.io :as io]
            [compojure.core :refer [context defroutes GET OPTIONS POST]]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [puns.greyt :refer [pen-pun]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer :all]
            [stencil.core :refer [render-file]])
  (:gen-class))

(defroutes
  ^{:private true}
  app
  (GET "/" []
    (render-file "templates/index"
                 {:title "The Pun Machine"
                  :anti-forgery-token ((constantly *anti-forgery-token*))})) 
  (POST "/" {{q :q} :params}
    (pen-pun q))
  (route/resources "/"))

(defonce ^{:private true} server (atom nil))

(defn start-server
  "Starts the server on port 3000 or `$PORT`."
  [] (let [port (or (some-> (System/getenv "PORT") bigint int) 3000)]
       (->> (run-server (wrap-defaults app site-defaults) {:port port})
         (reset! server))
       (println (format "Punning on http://localhost:%d" port))))

(defn stop-server
  "Stops the server after 100ms."
  [] (when (some? @server)
       (@server :timeout 100)
       (reset! server nil)))

(defn -main
  "Calls [[start-server]]."
  [& args] (start-server))
