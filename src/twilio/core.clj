(ns twilio.core
  (refer-clojure :exclude [send])
  (import [java.net URLEncoder])
  (:require [clj-http.client :as client]
            [clojure.string :as str]))

(def *base* "https://api.twilio.com/2010-04-01")

;; Authentication info

(def ^:dynamic *sid*   "")
(def ^:dynamic *token* "")

;; Helper macro

(defmacro with-auth
  [account_sid auth_token & body]
  `(binding [*sid* ~account_sid
             *token* ~auth_token]
    (do ~@body)))

(defn encode-url [url] 
  (URLEncoder/encode url))

(defn make-request-url [endpoint]
  (format "%s/Accounts/%s/SMS/%s.json"
    *base*
    *sid*
    endpoint))

;; HTTP requests

(defn request
  "Make a simple POST request to the API"
  [method url & params]
  (assert ((complement every?) str/blank? [*sid* *token*]))
  (let [request-params (into {} params)]
    (try
      (client/request
        {:method method
         :url url
         :as :json
         :form-params request-params
         :basic-auth [*sid* *token*]})
    (catch Exception e
      {:error e}))))

(deftype SMS [from to body])

;; Utils

(def twilio-format-key
  (comp keyword str/capitalize name))

(defn as-twilio-map [m]
  "Twilio defines ugly uppercase keys for the api i.e :from becomes :From
   so this helper transforms clojure keys without making my eyes bleed"
  (reduce 
    (fn [acc [k v]]
      (conj acc {(twilio-format-key k) v})) {} m))
        
(defn sms 
  "Create an SMS message"
  [from to body]
  (as-twilio-map
    {:body body
     :to to
     :from from}))
 
(def msg
  (sms "+442033222504"
       "+447846012894"
       "OH HAI!"))

;; Send an SMS message via Twilio
;; *************************************************

(defn send
  "Send an SMS message
    msg is a map with the following keys
    - From
    - To
    - Body
  "
  [msg]
  (let [url (make-request-url "Messages")]
    (request :post url msg)))

;; *************************************************

(defn get-messages 
  "Fetch all messages sent from your account"
  []
  (if-let [response (request :get (make-request-url "Messages"))]
    (get-in response [:body :sms_messages])))

