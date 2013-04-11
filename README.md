# Twillio

Twilio API for Clojure

Twilio is a really neat service that lets you send SMS messages from your web apps. This is my incomplete attempt 
at wrapping some of the API functions nicely into native Clojure code.

There is a fully complete Java API out there so if you're looking to use Twilio from Clojure that's a good option.

## Usage

### SMS MESSAGES

First require the Twilio sms ns

```
(:require [twilio.core :as twilio]))
```

Send an SMS message

```clojure
(twilio/with-auth "your-sid" "your-auth-token"
  (twilio/send {:From "+442033222504"
                :To "+447846012894"
                :Body "Hello world"}))

```

## License

Copyright © 2012 Owain Lewis

Distributed under the Eclipse Public License, the same as Clojure.
