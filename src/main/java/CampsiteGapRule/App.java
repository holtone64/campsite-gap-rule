package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton, All rights reserved */
/**
 * @author holtone64
 *
 */

public class App {

	public static void main(String[] args) {
		CampsiteAvailability campsiteAvailability = new CampsiteAvailability(new AvailabilityDisplay());
		
		// The CampsiteAvailability object can also be initialized with a path to a JSON file or 
//		campsiteAvailability = 
//				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
//						new AvailabilityDisplay());
		
		// get the JSON path from the user in the console
		campsiteAvailability.setJsonObject(campsiteAvailability.getAvailabilityDisplay().queryJsonPath());
		
		// Send our now populated and processed list of campsites to the AvailabilityDisplay object's displayAvailableCampsites() method
		// to print the available campsites to the console
		campsiteAvailability.getAvailabilityDisplay().displayAvailableCampsites(campsiteAvailability.getCampsites());
	}

}
