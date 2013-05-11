HTTP JSON
=========

Version: 0.0.1

A very simple Java library for making requests for JSON and parsing the
responses.

You can download a pre-built version of the JAR at:
http://files.danielge.org/http-json/http-json-0.0.1.jar

Usage example
-------------

This library is fairly straightforward to use, but here is an example.

Let's say we want to find the current location of the ISS using the
[Open-Notify](http://open-notify.org/api-doc#iss-now) API. We design a class
structure as follows.

    class ISSNowResponse {
        private long timestamp;
        private String message;

        private LatLng iss_position;

        private class LatLng {
            private double latitude;
            private double longitude;

            public Tuple<Double,Double> toTuple() {
                return new Tuple<Double, Double>(latitude, longitude);
            }
        }
    }

For more information on how to construct such classes, see the [GSON
documentation](https://sites.google.com/site/gson/gson-user-guide#TOC-Using-Gson).

To make the JSON request:

    JsonResponse response = JsonRequest.get("http://api.open-notify.org/iss-now/");

To decode the JSON response, we could write something like this:

    if (response.getResponseCode() == 200) {    // HTTP OK
        ISSNowResponse issNow = response.fromJson(ISSNowResponse.class);
        // ...
    }
