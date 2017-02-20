# campsite-gap-rule
basic implementation of a campsite gap rule that disallows the creation of schedule gaps of a certain size

test

The App class demonstrates how to properly initialize and use the CampsiteAvailabilityQuery and AvailabilityDisplay classes. The CampsiteAvailabilityQuery class will accept both a org.json.simple.JSONObject as well as a String as constructor arguments. The justification for this was that if these classes were to be used in a real world setting, the JSON data would probably not come from a file already written to disk, and most JSONObjects can be converted to strings on the fly.   

in a production system, dates before today's would be prohibited, but the sample file has dates from last summer.

I put in two important checks to ignore reservations currently in the system that violate our gap rules, as well as for cases like in Campsite 2 in our test file where the existing reservation's date range would actually fit inside our target reservation.  

The CampsiteAvailability object is the 'manager' object that uses Campsite and Reservation objects in order to calculate our available campsites from the JSON input.  

In order to make date math easier to implement and maintain, I used Java 8 date classes.  Joda Time was considered, but the Java 8 classes were sufficient to complete the task.  

Maven is used as the build system for both the main program and the test suite.
      