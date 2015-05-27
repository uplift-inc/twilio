(ns twilio.core
  (import [java.net URLEncoder])
  (:require [clj-http.client :as http]
            [clojure.string :as str]))

(def base "https://api.twilio.com/2010-04-01")

;; Authentication info

(def ^:dynamic *sid*   "")
(def ^:dynamic *token* "")

(defn env [var] (System/getenv var))

(defn load-auth-from-env []
  (let [sid (env "TWILIO_SID")
        token (env "TWILIO_AUTH_TOKEN")]
   {:sid sid :token token}))

(defn load-auth-from-config []
  (binding [*read-eval* false]
    (with-open [r (clojure.java.io/reader "config.clj")]
      (read (java.io.PushbackReader. r)))))

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
    base
    *sid*
    endpoint))

;; HTTP requests

(defn request
  "Make a simple POST request to the API"
  [method url & params]
  (assert ((complement every?) str/blank? [*sid* *token*]))
  (let [request-params (into {} params)]
    (try
      (http/request
        {:method method
         :url url
         :form-params request-params
         :basic-auth [*sid* *token*]
         :conn-timeout 3000}) ; Timeout the request in 3 seconds
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

;; Send an SMS message via Twilio
;; *************************************************

(defn send-sms
  "Send an SMS message which is a map in the form {:From x :To x :Body x}"
  [params]
  (let [url (make-request-url "Messages")]
    (request :post url params)))

;; *************************************************

(defn get-messages
  "Fetch all messages sent from your account"
  []
  (if-let [response (request :get (make-request-url "Messages"))]
    (get-in response [:body :sms_messages])))

(defn verify-phone
  "Verify a phone number for making calls"
  [num]
  (assert ((complement every?) str/blank? [*sid* *token*]))
  (let [url (str base "/Accounts/" *sid* "/OutgoingCallerIds.json")
        params {:PhoneNumber num}]
    (request :post url params)))

(defn make-call
  "Make a phone call"
  [params]
  (assert ((complement every?) str/blank? [*sid* *token*]))
  (let [url (str base "/Accounts/" *sid* "/Calls.json")]
    (request :post url params)))
