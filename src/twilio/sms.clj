(ns twilio.sms
  (:use [twilio.core])
  (refer-clojure :exclude [send]))

(def msg {:From "+442033222504" :To "+447846012894" :Body "Hello world"})

;; SMb6044b73f72a53bfd508886f3b4c9cf7

;; Fetch a single SMS message
(defn get-message [sid])
  
(defn send
  "Send an SMS message
    msg is a map with the following keys
    - From
    - To
    - Body 
  "
  [msg]
  (let [url (make-request-url)]
    (core/make-request url p))))

(comment 
  (with-auth "" ""
    (send msg)))
  
