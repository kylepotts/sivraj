(ns slackme.command_matcher
  (:require [slackme.commands :as commands]))

(defn get-command-name [command]
  (let [args (clojure.string/split command #" ")]
    (if (= (clojure.string/lower-case (first args)) "sivraj")
      (second args))))

(defn run-command [command]
  (let [command-key (keyword (get-command-name command))]
    (if (not= command-key nil)
      ((get commands/command-map command-key)))))