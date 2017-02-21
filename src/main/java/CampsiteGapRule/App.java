package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton */
/**
 * @author holtone64
 *
 */

public class App {

	public static void main(String[] args) {
		CampsiteAvailability campsiteAvailability = new CampsiteAvailability(new AvailabilityDisplay());
		campsiteAvailability.setJsonObject(campsiteAvailability.getAvailabilityDisplay().queryJsonPath());
		campsiteAvailability.getAvailabilityDisplay().displayAvailableCampsites(campsiteAvailability.getCampsites());
	}

}
