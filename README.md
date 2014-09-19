# Twillio

Twilio SMS library for Clojure. Available from Clojars.

```
[twilio-api "1.0.0"]
```

Twilio is a really neat service that lets you send SMS messages from your web apps.

This wraps some of the SMS functions nicely into a lightweight library.

There is a fully complete Java API out there so if you're looking to use Twilio from Clojure that's a good option if you
need more than just SMS functionality.

## Usage

### SMS MESSAGES

First require the Twilio sms ns

```
(:require [twilio.core :as twilio]))
```

Send an SMS message

```clojure
(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/send
    {:From "+442033222504"
     :To "+447846012894"
     :Body "Hello world"}))
```

Since uppercase key names hurt my eyes, there's a helper function to make things cleaner

```clojure
(def my-sms-message
  (sms "+442033222504" ;; from
       "+447846012894" ;; to
       "OH HAI!"))     ;; message

;; Send the message

(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/send my-sms-message))

```

Fetch all your sent messages

```clojure
(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/get-messages))
```

## License

Copyright © 2013 Twilio Owain Lewis

Distributed under the Eclipse Public License, the same as Clojure.
