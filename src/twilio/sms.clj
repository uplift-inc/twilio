(ns twilio.sms
  (:use [twilio.core])
  (refer-clojure :exclude [send]))

(def msg {:From "+442033222504" :To "+447846012894" :Body "Hello world"})

(defn sms-get-url []
  "https://api.twilio.com/2010-04-01/Accounts/AC05a4e66a95f88d7b2d6dc639e8c19f3e/SMS/Messages/SMb6044b73f72a53bfd508886f3b4c9cf7.json")

;; SMb6044b73f72a53bfd508886f3b4c9cf7

(defn get-message [sid]
  
(defn send [msg]
  (let [url (make-request-url)]
    (core/make-request url p))))

(comment 
  (with-auth "" ""
    (send msg)))
  
