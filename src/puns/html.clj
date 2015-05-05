(ns puns.html
  (:require [hiccup.core :refer [html]]
            [hiccup.form :refer [hidden-field submit-button]]
            [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.util :refer [to-uri with-base-url]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]])
  (:import (hiccup.util ToURI)))

(defn og-meta [m]
  (let [kf #(keyword (str "og" (when (string? %) ":") %))]
    (for [[k v] m] [:meta {:property (kf k), :content v}])))

(defn head [{:keys [og-image title]}]
  (let [og-image (to-uri og-image)]
    [:head
     [:title title]
     [:link {:ref "icon", :type "image/png", :href og-image}]
     (include-css (to-uri "/css/styles.css"))
     [:meta {:name "viewport", :content "width=320"}]
     (og-meta {:description "The greyest thing you've ever."
               :image       og-image
               :type        "website"})]))

(defn punsonpunsonpuns []
  [:legend
   [:p {:id "punsonpunsonpuns"}
    [:span              "Puns on"] [:br]
    [:span {:id "pun2"} "puns on"] [:br]
    [:span {:id "pun3"} "puns"]]])

(defn anti-forgery [token] (hidden-field "__anti-forgery-token" token))

(defn say [pun]
  [:script {:type "text/javascript"}
   "function say(b){var a=new SpeechSynthesisUtterance();"
   "a.text=b;a.lang=\"ru\";a.onerror=function(c){"
   "alert(\"Get a better browser, bro!\")};"
   "speechSynthesis.speak(a)};"
   "say(" (pr-str pun) ")"])

(defn form [pun]
  (list (when pun (say pun))
        [:form {:action  "/"
                :enctype "multipart/form-data"
                :id      "pun-machine"
                :method  "post"}
         [:fieldset
          (punsonpunsonpuns)
          [:input (cond->
                      {:autofocus   true
                       :id          "q"
                       :name        "q"
                       :placeholder "This is the greatest app."
                       :required    true
                       :type "text"}
                    pun (assoc :value pun))]]
         [:fieldset (submit-button "#GETIT")]
         (anti-forgery *anti-forgery-token*)]))

(defn footer []
  [:footer
   [:p "brought to you by "
    [:a {:href "http://www.endoftheinternet.com", :target "_blank"}
     "no@thanks.com"]]])

(defn main-page [& [pun]]
  (with-base-url "http://puns.ericb.me"
    (html5
     {:prefix "og: http://ogp.me/ns#"}
     (html
      (head {:og-image "/img/50percentgrey.png"
             :title    "The Pun Machine 4.20"})
      [:body
       (form pun)
       (footer)]))))
