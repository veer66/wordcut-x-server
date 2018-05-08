(defproject rockers.veer66/wordcut-x-server "0.1.0-SNAPSHOT"
  :description "A web-api server for wordcut-x"
  :url "https://github.com/veer66/wordcut-x-server"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.1"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler rockers.veer66.wordcut-x-server.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
