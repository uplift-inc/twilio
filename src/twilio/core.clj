(ns twilio.core
  (refer-clojure :exclude [send])
  (import [java.net URLEncoder])
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(def ^:dynamic *base* "https://api.twilio.com/2010-04-01")
(def ^:dynamic *sid* "")
(def ^:dynamic *token* "")

(defmacro with-auth
  [account_sid auth_token & body]
  `(binding [*sid* ~account_sid
             *token* ~auth_token]
    (do ~@body)))

(defn encode-url [url] (URLEncoder/encode url))

(defn make-request-url []
  (format
    "%s/Accounts/%s/SMS/Messages.json"
    *base*
    *sid*))

(defn request
  "Make a generic HTTP request"
  [method url & params]
  (try
    (let [f (condp = method
              :post client/post
              :else client/get)]
    (f url
      {:accept :json
       :form-params (first params)
       :basic-auth [*sid* *token*]}))
  (catch Exception e
     (let [exception-info (.getData e)]
     (select-keys
       (into {} (map (fn [[k v]] [(keyword k) v])
         (json/parse-string
             (get-in exception-info [:object :body]))))
             (vector :status :message :code))))))

(def msg {:From "+442033222504"
          :To "+447846012894"
          :Body "Hello world"})

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
    (request :post url msg)))

