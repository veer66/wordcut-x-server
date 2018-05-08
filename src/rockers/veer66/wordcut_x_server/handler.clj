(ns rockers.veer66.wordcut-x-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.data.json :as json]
            [clojure.string :as str]
            [rockers.veer66.wordcut_x_clj.wordcut :refer [create-wordcut-from-url wordseg build-dag]]
            )
  (:import rockers.veer66.Wordcut
           rockers.veer66.EdgeType))

(def wc (create-wordcut-from-url "http://file.veer66.rocks/dix/tdict-std.txt"))

(defn convert-etype [etype]
  (->> etype
       .toString
       str/capitalize))

(defn convert-edge [edge]
  {:s (.s edge)
   :e (.e edge)
   :etype (-> edge
              .etype
              convert-etype)})

(defn convert-edges [edges]
  (map convert-edge edges)) 

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
