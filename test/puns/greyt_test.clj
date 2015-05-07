(ns puns.greyt-test
  (:require [clojure.test :refer [deftest is]]
            [puns.greyt :refer [ey? greyify pen-pun]]))

(deftest hey (is (true? (ey? "hey"))))

(deftest nope (is (false? (ey? "nope"))))

(deftest greyvorite
  (is (= ["G" "R" "EY1" "V" "ER0" "IH0" "T"] (greyify "favorite"))))

(deftest silly (is (= "silly" (pen-pun "silly"))))
