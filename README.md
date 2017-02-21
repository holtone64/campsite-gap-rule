# campsite-gap-rule
Description:

basic implementation of a campsite gap rule that disallows the creation of schedule gaps of a certain size

High level strategy: create 'Campsite' objects based on our JSON data, and 'Reservation' objects based on the JSON data as well. The Reservations are added to each Campsite as a list.  Next, the target reservation is added to each of the Campsite's reservations arrays.  If the new reservation overlaps a reservation already in place, the new reservation is not added and the campsite's 'available' boolean is set to false.  The 'available' booleans are used to track whether the campsites should be included in our final list of available sites.  We then loop over our reservation lists to check whether any of our gap rules are violated by the addition of the new reservation.  In order to avoid doing date comparisons on existing reservations, each reservation is given an 'existingReservation' boolean flag. If we are looking at two reservations with the 'existingReservation' flag set to true, the date comparison is not done because we know we are not looking at a new reservation.  If any of the date comparisons violate our gap rules, we set the campsite's 'available' boolean to false.  After the comparisons are done, we loop through our list of campsites and output the name value if the 'available' boolean is still true.              

Assumptions: 

I did not create a separate project/jar for the test cases to avoid unnecessary complexity, thinking that allowing the tests to be run via Maven's "test" phase would satisfy the "executable" component of that specification.    

Building and running: 

Maven is used as the build system.  In order to run the program and tests associated with this program in Eclipse, first download the project via the git URL, import the project as a Maven project in Eclipse.  Then, create a new debug/run configuration of the "Maven Build" configuration type.  Next, enter "clean package" into the "Goals" field.  In order to skip the tests, check the "skip tests" checkbox in the run configuration dialog box or add "-Dmaven.test.skip=true" to the 'goals' text box.

When the program asks for the path to a valid JSON file, you can enter "/test-case.json" to see the test values, or another valid path if you have another JSON file with the correct keys on your system.  The original test file should be packaged properly by Maven.    

You can also use the Maven command line tool by cd'ing into the project directory and entering "mvn clean package" if you have Maven installed.  To skip tests in the command line, the "-Dmaven.test.skip=true" flag can be used again.   

Maven will construct a jar and add it to the /target folder. The program will automatically run in the 'package' phase of deployment

If you want to run the jar directly, navigate to the /target folder and execute the "campsiteGapRule-0.3-jar-with-dependencies" jar

The App class demonstrates how to properly initialize and use the CamspiteAvailability and AvailabilityDisplay classes. The CampsiteAvailabilityQuery class can accept a org.json.simple.JSONObject as well as a file path as a String as constructor arguments. The CampsiteAvailability object can be  initialized without a JsonObject or the JSONObject's path as a String, but the JSONObject must be added later.  If the CampsiteAvailability object has been initialized without the JSON, the JSON can be added via the AvailabilityDisplay.queryJsonPath() method that asks the user for the path to a JSON file in the console.   
      
      
Notes

in a production system, dates before today's would be prohibited, but the sample file has dates from last summer.

In order to avoid extra processing with reservations, each campsite's reservation list is kept separately.  

I put in two important checks to 1. ignore reservations currently in the system that violate our gap rules, and 2. for cases like in Campsite 2 in our test file where the existing reservation's date range would actually fit inside our target reservation.  

In order to make date math easier to implement and maintain, Java 8 LocalDate class is used.  Joda Time was considered, but the Java 8 classes were sufficient to complete the task.