(ns slackme.core
  (:gen-class)
  (:require [slack-rtm.core :refer [connect sub-to-event send-event]]
            [environ.core :refer [env]]))

; connect to slack
(def slack-auth-token (env :slack-auth-token))
(def rtm-conn (connect slack-auth-token))

; send events and listen for events
(def dispatcher (:dispatcher rtm-conn))
(def events-publication (:events-publication rtm-conn))

; set up receivers
(def hello-receiver #(println "Sucessfully connected to Real Time API:" %))
(sub-to-event events-publication :hello hello-receiver)

(def message-receiver
  (fn [s]
    (let [{channel :channel message :text} s]
      (println channel message)
      (send-event dispatcher {:type "message" :channel channel :text "Hello"} ))))
(sub-to-event events-publication :message message-receiver)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (env :slack-auth-token))
  (println "Hello, World!"))
