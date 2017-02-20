package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton */
/**
 * @author holtone64
 *
 */
/* Copyright (c) 2017 Eric Holton */

public class App {

	public static void main(String[] args) {
//		CampsiteAvailability campsiteAvailability = 
//				new CampsiteAvailability(CampsiteAvailability.readInputFile("\test-case.json"), 
//						new AvailabilityDisplay());
		CampsiteAvailability campsiteAvailability = 
		new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
				new AvailabilityDisplay());
	}

}
