;; -*- mode: clojure; mode: typed-clojure -*-

(ns puns.greyt
  "Greyt punster"
  {:author "Eric Bailey"}
  (:import (clojure.lang LazySeq PersistentVector)
           (java.io BufferedReader)
           (java.lang CharSequence)
           (java.net URL)
           (java.util.regex Pattern))
  (:require [clj-leveldb :as ldb]
            [clojure.core.typed :refer [Any ASeq AVec HMap HVec IFn U
                                        ann ann-form defalias loop]]
            [clojure.java.io :refer [reader resource]]
            [clojure.string :refer [split upper-case]]
            [opennlp.nlp :refer [make-detokenizer make-tokenizer]]
            [taoensso.nippy :as nippy])
  (:refer-clojure :exclude [loop]))

;; ===== TYPE ALIASES =====

(defalias ABoolean (U Boolean nil))

(defalias AString (U String nil))

(defalias AVecString (U (PersistentVector String) nil))

(defalias AAVecString (U (AVec String) nil))

(defalias LevelDBConfig
  (HMap :mandatory {:val-decoder [Any -> java.lang.String]
                    :key-encoder [(PersistentVector String) -> Any]
                    :key-decoder [Any -> Any]}
        :complete? true))


;; ===== MISC ANNOTATIONS =====

(ann ^:no-check clojure.core/line-seq
     (IFn [BufferedReader -> (LazySeq String)]))

(ann ^:no-check clojure.core/some? (IFn [Any -> Boolean]))

(ann ^:no-check clojure.java.io/reader (IFn [URL -> BufferedReader]))

(ann ^:no-check clojure.java.io/resource (IFn [String -> URL]))

(ann ^:no-check byte-streams/to-string (IFn [Any -> String]))

(ann ^:no-check clj-leveldb/get (IFn [Any AAVecString -> AString]))

(ann ^:no-check clj-leveldb/create-db (IFn [String LevelDBConfig -> Any]))

(ann ^:no-check opennlp.nlp/make-detokenizer (IFn [URL -> Any]))

(ann ^:no-check opennlp.nlp/make-tokenizer (IFn [URL -> Any]))

(ann ^:no-check taoensso.nippy/freeze (IFn [Any -> Any]))

(ann ^:no-check taoensso.nippy/thaw (IFn [Any -> Any]))


;; ===== MAIN =====

(ann db Any)
(defonce db
  (ldb/create-db (ann-form "/tmp/pun-machine" String)
                 (ann-form {:key-decoder nippy/thaw
                            :key-encoder nippy/freeze
                            :val-decoder byte-streams/to-string}
                           LevelDBConfig)))

(def tokenize (make-tokenizer (resource "models/en-token.bin")))

(def detokenize (make-detokenizer (resource "models/english-detokenizer.xml")))

(ann is-consonant? (IFn [String -> Boolean]))
(defn is-consonant? [s]
  (and (= (count s) 1)
       (. "BCDFGHJKLMNPQRSTVWXZ" contains (subs s 0 1))))

(ann is-word? (IFn [String -> (IFn [String -> AAVecString])]
                   [String String -> AAVecString]))
(defn- is-word?
  ([word]
   (fn [line] (is-word? word line)))
  ([word line]
   (let [[k v] (split line #"  ")]
     (when (and (= k word) (string? v))
       (split v #" ")))))

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
