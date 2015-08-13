(defproject me.ericb.puns "4.2.0-SNAPSHOT"
  :description  "Puns on puns on puns on the internet."
  :url          "http://puns.ericb.me"
  :license      {:name "MIT License"
                 :url  "http://yurrriq.mit-license.org/2014/"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.107"]
                 #_[org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/core.typed "0.3.10"]
                 [clojure-opennlp "0.3.3"]
                 [com.taoensso/nippy "2.9.0"]
                 [factual/clj-leveldb "0.1.1"]
                 [cheshire "5.5.0"]
                 #_[clj-fuzzy "0.2.1"]
                 [compojure "1.4.0"]
                 [environ "1.0.0"]
                 [garden "1.2.6" :exclusions [org.clojure/clojure]]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.19"]
                 #_[reagent "0.5.0"]
                 [ring/ring-defaults "0.1.5"]]

  :main          ^:skip-aot puns.core

  :profiles      {:uberjar {:aot :all}})
