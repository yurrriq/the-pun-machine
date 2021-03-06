(ns puns.core
  "punXcore"
  {:author "Eric Bailey"}
  (:require [clojure.string :refer [join]]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :as route]
            [org.httpkit.server :refer [run-server]]
            [puns.greyt :refer [detokenize pen-pun tokenize]]
            [puns.html :as html]
            [puns.css :as css]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.middleware.defaults :refer :all])
  (:gen-class))

(let [url "http://puns.ericb.me"]
  (defroutes ^:private app
    (GET "/" []
      (html/main-page url))
    (GET "/css/styles.css" []
      {:content-type "text/css"
       :body (css/styles)})
    (POST "/" {{q :q} :params}
      (->> (tokenize q)
           (map pen-pun)
           (map (fn [pun] (if (vector? pun) (join " " pun) pun)))
           (detokenize)
           (html/main-page url)))
    (route/resources "/")))

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
