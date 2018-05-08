(ns rockers.veer66.wordcut-x-server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.data.json :as json]
            [clojure.string :as str]
            )
  (:import rockers.veer66.Wordcut
           rockers.veer66.EdgeType))

(def dix-url (.toURL (java.net.URI/create "http://file.veer66.rocks/dix/tdict-std.txt")))
(def wc (Wordcut/fromDixUrl dix-url))

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
      (json/write-str {:words (.segmentToStrList wc text)})))
  (POST "/dag" {body :body}
    (let [text (:text (json/read-str (slurp body)
                                     :key-fn keyword))]
      (->> (.buildDag wc text)
          (map convert-edges)
          json/write-str)))
  (route/not-found "Not Found"))
