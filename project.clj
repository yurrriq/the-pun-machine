(defproject me.ericb.puns "4.2.0-SNAPSHOT"
  :description "Puns on puns on puns on the internet."
  :url "http://puns.ericb.me"
  :license {:name "MIT License"
            :url "http://yurrriq.mit-license.org/2014/"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.typed "0.2.87"]
                 [clojure-opennlp "0.3.3"]
                 [com.taoensso/nippy "2.8.0"]
                 [factual/clj-leveldb "0.1.1"]
                 #_[clj-fuzzy "0.2.1"]
                 [compojure "1.3.3"]
                 [http-kit "2.1.19"]
                 [ring/ring-defaults "0.1.4"]
                 [stencil "0.3.5"]]
  
  :main ^:skip-aot puns.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
