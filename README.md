# campsite-gap-rule
Description:

basic implementation of a campsite gap rule that disallows the creation of schedule gaps of a certain size

The App class demonstrates how to properly initialize and use the CampsiteAvailabilityQuery and AvailabilityDisplay classes. The CampsiteAvailabilityQuery class will accept both a org.json.simple.JSONObject as well as a file path as a String as constructor arguments. The justification for this was that if these classes were to be used in a real world setting, the JSON data would probably not come from a file already written to disk, and most JSONObjects can be converted to strings on the fly.   

in a production system, dates before today's would be prohibited, but the sample file has dates from last summer.

I put in two important checks to ignore reservations currently in the system that violate our gap rules, as well as for cases like in Campsite 2 in our test file where the existing reservation's date range would actually fit inside our target reservation.  

The CampsiteAvailability object is the 'manager' object that uses Campsite and Reservation objects in order to calculate our available campsites from the JSON input.  

In order to make date math easier to implement and maintain, Java 8 LocalDate class is used.  Joda Time was considered, but the Java 8 classes were sufficient to complete the task.

Need to make sure our daysBetween value represents days between an existing reservation and a new one in addition to matching our gapSize before adding the campsite id to our invalidatedCampsiteIds list. This prohibits false negatives coming from reservation already in the system that violate our gap rules.

Assumptions: 

I did not create a separate project/jar suite for the test cases to avoid unnecessary complexity, thinking that allowing the tests to be run via Maven's "test" phase would satisfy the "executable" component of that specification.    

Building: 

Maven is used as the build system.  In order to run the program and tests associated with this program in Eclipse, first create a new debug/run configuration of the "Maven Build" configuration type.  In order to run the tests, enter "test" in the "Goals" field of your debug/run configuration. In order to run the program itself, enter "clean package" into the "Goals" field.  These goals can be combined to run sequentially by entering "test clean package" in the "Goals" field.  You can also use the Maven command line tool by cd'ing into the project directory and entering "mvn test", "mvn clean package", or "mvn test clean package" if you have Maven installed.         
      