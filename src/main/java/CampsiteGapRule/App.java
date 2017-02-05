package CampsiteGapRule;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author eric
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// create a JSON object from a JSON input file
//		JSONParser parser = new JSONParser();
//		try {
//			Object inputFile = parser.parse(new FileReader("resources/test-case.json"));
//			JSONObject jsonObj = (JSONObject) inputFile;
//			
//			// test value
//			//JSONObject jsonObj = new JSONObject("{}");
//			
//			// The CampsiteManager class requires a JSON Object and AvailabilityDisplay Object to initialize
//			CampsiteAvailabilityQuery campsiteAvailabilityQuery = new CampsiteAvailabilityQuery(jsonObj, new AvailabilityDisplay());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		CampsiteAvailabilityQuery campsiteAvailabilityQuery = 
//				new CampsiteAvailabilityQuery("resources/test-case.json", 
//						new AvailabilityDisplay());
		
		CampsiteAvailabilityQuery campsiteAvailabilityQuery = 
				new CampsiteAvailabilityQuery(CampsiteAvailabilityQuery.readInputFile("resources/test-case.json"), 
						new AvailabilityDisplay());
		
	}

}
