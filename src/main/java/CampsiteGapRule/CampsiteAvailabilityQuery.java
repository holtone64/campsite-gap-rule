package CampsiteGapRule;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CampsiteAvailabilityQuery {
	// do we want the CampsiteManager to manage its own display?
	private JSONObject jsonObj;
	private AvailabilityDisplay availabilityDisplay;
	
	public CampsiteAvailabilityQuery(String inputFilePath, AvailabilityDisplay availabilityDisplay) {
		this.availabilityDisplay = availabilityDisplay;
		jsonObj = readInputFile(inputFilePath);
		
		// 
	}
	
	public CampsiteAvailabilityQuery(JSONObject jsonObj, AvailabilityDisplay availabilityDisplay) {
		this.jsonObj = jsonObj;
		this.availabilityDisplay = availabilityDisplay;
		
		System.out.println("test");
		// 
	}
	
	public static JSONObject readInputFile(String inputFilePath) {
		// convenience method to create a org.json.simple.JSONObject from input file
		// currently static because it will exclusively be used before initialization
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
	
	private void ProcessQuery() {
		
	}
	
}