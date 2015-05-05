(ns puns.css
  (:require [garden.core :refer [css]]
            [garden.units :refer [px]]))

(defn body []
  [:body {:background "#eee"
          :color "#111111"
          :font-family ["HelveticaNeue-Light" "Helvetica Neue Light"
                        "Helvetica Neue" "Helvetica" "Arial"
                        "Lucida Grande" "sans-serif"]
          :pading (px 20)
          :text-align "center"}])

(defn form []
  [:form#pun-machine
   {:background "#666"
    :-moz-border-radius  (px 5)
    :-webkit-border-radius (px 5)
    :border-radius (px 5)
    :padding (px 20)
    :width (px 400)
    :margin-top (px 20)
    :margin-right "auto"
    :margin-left "auto"}
   [:fieldset {:border "none", :margin-bottom (px 10)}]
   [:fieldset:last-of-type {:margin-bottom 0}]
   [:legend
    {:color "#fff"
     :font-size (px 16)
     :font-weight "bold"
     :padding-bottom (px 10)
     :text-shadow "0 1px 1px #111"}]
   [:input
    {:background "#ffffff"
     :border "none"
     :-moz-border-radius (px 3)
     :-webkit-border-radius (px 3)
     :-khtml-border-radius (px 3)
     :border-radius (px 3)
     :font "italic 13px Georgia, \"Times New Roman\", Times, serif"
     :outline "none"
     :padding (px 5)
     :text-align "center"
     :width (px 200)}]
   ["input:not([type=submit])"
    {:width "100%"}]
   ["input:not([type=submit]):focus" :textarea:focus
    {:background "#eaeaea"}]
   [:button
    {:background "#384313"
     :border "none"
     :-moz-border-radius (px 20)
     :-webkit-border-radius (px 20)
     :-khtml-border-radius (px 20)
     :border-radius (px 20)
     :color "#ffffff"
     :display "block"
     :font "18px Georgia, \"Times New Roman\", Times, serif"
     :letter-spacing (px 1)
     :margin "auto"
     :padding "7px 25px"
     :text-shadow "0 1px 1px #000000"
     :text-transform "uppercase"}]
   [:button:hover {:background "#1e2506", :cursor "pointer"}]])

(defn styles []
  (css [:html :body :h1 :form :fieldset :legen :ol :li {:margin 0, :padding 0}]
       (body)
       [:#punsonpunsonpuns {:line-height "25%"}]
       [:#pun2 {:margin-left "50%", :position "relative", :top (px 2)}]
       [:#pun3 {:margin-left "15%", :position "relative", :top (px 5)}]
       (form)
       [:footer [:a {:color "#666"}]]))
