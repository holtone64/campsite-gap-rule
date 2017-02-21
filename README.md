# campsite-gap-rule
Description:

basic implementation of a campsite gap rule that disallows the creation of schedule gaps of a certain size

High level strategy: create 'Campsite' objects based on our JSON data, and 'Reservation' objects based on the JSON data as well. The Reservations are added to each Campsite as a list.  Next, the target reservation is added to each of the Campsite's reservations arrays.  If the new reservation overlaps a reservation already in place, the new reservation is not added and the campsite's 'available' boolean is set to false.  The 'available' booleans are used to track whether the campsites should be included in our final list of available sites.  We then loop over our reservation lists to check whether any of our gap rules are violated by the addition of the new reservation.  In order to avoid doing date comparisons on existing reservations, each reservation is given an 'existingReservation' boolean flag. If we are looking at two reservations with the 'existingReservation' flag set to true, the date comparison is not done because we know we are not looking at a new reservation.  If any of the date comparisons violate our gap rules, we set the campsite's 'available' boolean to false.  After the comparisons are done, we loop through our list of campsites and output the name value if the 'available' boolean is still true.          

The App class demonstrates how to properly initialize and use the CampsiteAvailabilityQuery and AvailabilityDisplay classes. The CampsiteAvailabilityQuery class can accept a org.json.simple.JSONObject as well as a file path as a String as constructor arguments. The CampsiteAvailability object can be  initialized without a JsonObject or the JSONObject's path as a String, but the JSONObject must be added later.  a convenience method has been added to the AvailabilityDisplay that asks the user for a valid JSON file path in the console.      

Assumptions: 

I did not create a separate project/jar suite for the test cases to avoid unnecessary complexity, thinking that allowing the tests to be run via Maven's "test" phase would satisfy the "executable" component of that specification.    

Building: 

Maven is used as the build system.  In order to run the program and tests associated with this program in Eclipse, first create a new debug/run configuration of the "Maven Build" configuration type.   In order to run the tests cases and the program, enter "clean package" into the "Goals" field.  In order to skip the tests, check the "skip tests" checkbox in the run configuration dialog box or add "-Dmaven.test.skip=true" to the 'goals' text box.  You can also use the Maven command line tool by cd'ing into the project directory and entering "mvn clean package" if you have Maven installed.  To skip tests in the command line, the "-Dmaven.test.skip=true" flag can be used again.           
      
      
Notes

in a production system, dates before today's would be prohibited, but the sample file has dates from last summer.

In order to avoid extra processing with reservations, each campsite's reservation list is kept separately.  

I put in two important checks to ignore reservations currently in the system that violate our gap rules, as well as for cases like in Campsite 2 in our test file where the existing reservation's date range would actually fit inside our target reservation.  

The CampsiteAvailability object is the 'manager' object that uses Campsite and Reservation objects in order to calculate our available campsites from the JSON input.  

In order to make date math easier to implement and maintain, Java 8 LocalDate class is used.  Joda Time was considered, but the Java 8 classes were sufficient to complete the task.

Need to make sure our daysBetween value represents days between an existing reservation and a new one in addition to matching our gapSize before adding the campsite id to our invalidatedCampsiteIds list. This prohibits false negatives coming from reservation already in the system that violate our gap rules.