# Twillio

Twilio API for Clojure

## Usage

### SMS MESSAGES

First require the Twilio sms ns

```
(:require [twilio.sms :as twilio]))
```

Send an SMS message

```clojure
(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/send {:From "+442033222504" 
                :To "+447846012894" 
                :Body "Hello world"}))

```

Get a single SMS message by SID

```clojure

(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/get-sms 1234))

```

## License

Copyright © 2012 Owain Lewis

Distributed under the Eclipse Public License, the same as Clojure.
