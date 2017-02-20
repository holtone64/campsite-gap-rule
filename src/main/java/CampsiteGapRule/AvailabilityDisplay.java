package CampsiteGapRule;

import java.util.List;

/* Copyright (c) 2017 Eric Holton */

/**
 * @author holtone64
 *
 */

public class AvailabilityDisplay {
	// the main purpose of this class is to separate the UI from the manager for extensibility and threading concerns
	
	public void displayAvailableCampsites(List<Campsite> campsites) {
		for (Campsite campsite : campsites) {
			if (campsite.getAvailable()) {
				System.out.println(campsite.getName());	
			}
		}
	}
}