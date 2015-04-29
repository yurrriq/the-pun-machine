(ns puns.css
  (:require [garden.core :refer [css]]
            [garden.units :refer [px]]))

(css [:#punsonpunsonpuns {:line-height "25%"}]
     [:#pun2 {:margin-left "50%", :position "relative", :top (px 2)}]
     [:#pun3 {:margin-left "15%", :position "relative", :top (px 5)}])
