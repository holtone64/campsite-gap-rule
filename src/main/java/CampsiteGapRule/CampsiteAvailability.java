package CampsiteGapRule;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class CampsiteAvailability {
	// do we want the CampsiteManager to manage its own display?
	private JSONObject jsonObj;
	private AvailabilityDisplay availabilityDisplay;
	private List<List> campsites;
	
//	public CampsiteAvailabilityQuery(String inputFilePath, AvailabilityDisplay availabilityDisplay) {
//		this.availabilityDisplay = availabilityDisplay;
//		jsonObj = readInputFile(inputFilePath);
//	}
	
	public CampsiteAvailability(String jsonAsString, AvailabilityDisplay availabilityDisplay) {
		this.availabilityDisplay = availabilityDisplay;
		jsonObj = createSimpleJsonObjectFromString(jsonAsString);
		processData();
	}
	
	public CampsiteAvailability(JSONObject jsonObj, AvailabilityDisplay availabilityDisplay) {
		this.jsonObj = jsonObj;
		this.availabilityDisplay = availabilityDisplay;
		
		System.out.println("test");
		processData();
	}
	
	public static JSONObject readInputFile(String inputFilePath) {
		// convenience method to create a org.json.simple.JSONObject from input file
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			Object inputFile = parser.parse(new FileReader(inputFilePath));
			jsonObj = (JSONObject) inputFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	public JSONObject createSimpleJsonObjectFromString(String inputString) {
		// convenience method to convert a string to a org.simple.JSONObject. 
		return (JSONObject) JSONValue.parse(inputString);
	}
	
	private void processData() {
		// create array of java.something.date objects based on our array of existing reservations: "reservations" JSON key
		// compare +- gapRules to each
		// need array of gaprules
		// if our desired date == any gaprules, fail
		// for each reservation, 
		// create array list of campsites with indeces mapping to id numbers from JSON file
		// switch boolean values to false for a specific campsite when gap rules are broken, do lookup based on id in reservation id
		// 2-d arraylist for campsites, each internal array contains the JSON ID, name, and boolean whether campsite is available
		// loop over reservation list.  for each one, do our gap rule comparisons.  if any fail, lookup campsite, set boolean to false.
		
		readCampsites();
	}
	
	private void readCampsites() {
		campsites = new ArrayList<List>();
        JSONArray campsites = (JSONArray) jsonObj.get("campsites");
        Iterator<JSONObject> iterator = campsites.iterator();
        while (iterator.hasNext()) {
        	ArrayList campsite = new ArrayList();
        	JSONObject jsonCampsite = (JSONObject)iterator.next();
        	Long id = (Long)jsonCampsite.get("id");
        	String name = (String)jsonCampsite.get("name");
        	campsite.add(id);
        	campsite.add(name);
        	// the following boolean will be set to false if we find a gapRule violation
        	campsite.add(new Boolean (true));
        	this.campsites.add(campsite);
        }
        String boo = "zoo";
	}
	
}