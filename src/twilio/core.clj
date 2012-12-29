(ns twilio.core)

(def ^:dynamic *base* "https://api.twilio.com/2010-04-01")
(def ^:dynamic *sid* "")
(def ^:dynamic *token* "")

(defmacro with-auth [account_sid auth_token & body]
  `(binding [*sid* ~account_sid
             *token* ~auth_token]
    (do ~@body)))
        