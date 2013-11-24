(ns twilio.core-test
  (:use clojure.test
        twilio.core))

(deftest uppercase-keys
  (testing "Should convert keys to uppercase format"
    (let [actual (as-twilio-map {:foo 1 :bar 2 :baz 3})]
      (is (= {:Foo 1 :Bar 2 :Baz 3} actual)))))
