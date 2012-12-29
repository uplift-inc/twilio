(ns twilio.sms
  (refer-clojure :exclude [send]))

(defn send [{:keys [to from body]}]
  "Send an SMS message"
  (str to from body))