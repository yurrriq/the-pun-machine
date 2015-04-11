;; -*- mode: clojure; mode: typed-clojure -*-

(ns puns.greyt
  "Greyt punster"
  {:author "Eric Bailey"}
  (:import (clojure.lang LazySeq PersistentVector)
           (java.io BufferedReader)
           (java.lang CharSequence)
           (java.net URL)
           (java.util.regex Pattern))
  (:require [clojure.core.typed :refer [Any AVec IFn U ann defalias]]
            [clojure.java.io :refer [reader resource]]
            [clojure.string :refer [split upper-case]]))

;; ===== TYPE ALIASES =====

(defalias ABoolean (U Boolean nil))

(defalias AString (U String nil))

(defalias AVecString (U (PersistentVector String) nil))

(defalias AAVecString (U (AVec String) nil))


;; ===== MISC ANNOTATIONS =====

(ann ^:no-check clojure.core/line-seq
     (IFn [BufferedReader -> (LazySeq String)]))

(ann ^:no-check clojure.core/some? (IFn [Any -> Boolean]))

(ann ^:no-check clojure.java.io/reader (IFn [URL -> BufferedReader]))

(ann ^:no-check clojure.java.io/resource (IFn [String -> URL]))

;; FIXME: LAME HACK
(ann clojure.string/split (IFn [AString Pattern -> (PersistentVector String)]))


;; ===== MAIN =====

(ann is-word? (IFn [String -> (IFn [String -> AAVecString])]
                   [String String -> AAVecString]))
(defn- is-word?
  ([word]
   (fn [line] (is-word? word line)))
  ([word line]
   (let [[k v] (split line #"  ")]
     (when (= k word) (split v #" ")))))

(ann starts-with? (IFn [String -> (IFn [String -> Boolean])]))
(defn- starts-with? [^String s1] (fn [^String s2] (.startsWith s2 s1)))

(ann syllables (IFn [String -> AAVecString]))
(defn syllables
  "Takes a string a and returns a vector of its syllables, if it's found in
  the dictionary, otherwise nil."
  [word]
  (let [word (upper-case word)]
    (with-open [dict (reader (resource "cmudict.0.7a.txt"))]
      (some (is-word? word) (drop 212 (line-seq dict))))))

(ann ey? (IFn [String -> ABoolean]))
(defn ey?
  "Is this grey or not?"
  [word]
  (when-let [coll (syllables (upper-case word))]
    (some? (seq (filter (starts-with? "EY") coll)))))

(ann pen-pun (IFn [String -> String]))
(defn pen-pun
  "TODO: Make greyt puns."
  [s] s)
