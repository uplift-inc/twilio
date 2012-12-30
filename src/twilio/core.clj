(ns twilio.core
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

(defn encode-url [url]
  (URLEncoder/encode url))

(defn make-request-url []
  (format
    "%s/Accounts/%s/SMS/Messages.json"
    *base*
    *sid*))

;; TODO abstract this
(defn make-request 
  "Make a generic HTTP request"
  [url & params]
  (try
    (client/post url
      {:accept :json
       :form-params (first params)
       :basic-auth [*sid* *token*]})
  (catch Exception e
     (let [exception-info (.getData e)]
     (select-keys
       (into {} (map (fn [[k v]] [(keyword k) v])
         (json/parse-string
             (get-in exception-info [:object :body]))))
             (vector :status :message :code))))))
  