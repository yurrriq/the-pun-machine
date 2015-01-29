(defproject me.ericb.puns "0.1.0-SNAPSHOT"
  :description "Puns on puns on puns on the internet."
  :url "http://puns.ericb.me"
  :license {:name "MIT License"
            :url "http://yurrriq.mit-license.org/2014/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [http-kit "2.1.19"]
                 [ring/ring-defaults "0.1.3"]
                 [stencil "0.3.5"]]
  :main ^:skip-aot puns.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
