(ns rocks.veer66.wordcut-x-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.data.json :as json]
            [clojure.string :as str]
            [rockers.veer66.wordcut_x_clj.wordcut :refer [create-wordcut-from-url wordseg build-dag]]
            )
  (:import rockers.veer66.Wordcut
           rockers.veer66.EdgeType))

(def config (load-string (slurp (clojure.java.io/resource "config.clj"))))
(def dix-type (:dix-type config))

(def dix-url
  (cond
    (= dix-type :url) (:dix-url config)
    (= dix-type :resource) (.toString (clojure.java.io/resource (:dix-path config)))))

(def wc (create-wordcut-from-url dix-url))

(defroutes app
  (GET "/" [] "wordcut-x")
  (POST "/wordseg" {body :body}
    (let [text (:text (json/read-str (slurp body)
                                     :key-fn keyword))]
      (json/write-str {:words (wordseg wc text)})))
  (POST "/dag" {body :body}
    (let [text (:text (json/read-str (slurp body)
                                     :key-fn keyword))]
      (json/write-str (build-dag wc text))))
  (route/not-found "Not Found"))
