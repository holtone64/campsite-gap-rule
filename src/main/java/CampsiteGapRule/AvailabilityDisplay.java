package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton, All rights reserved */

import java.util.List;
import java.util.Scanner;

/**
 * @author holtone64
 *
 */

public class AvailabilityDisplay {
	private Scanner scanner;
	// the main purpose of this class is to separate the UI from the manager for extensibility and threading concerns
	
	public void displayAvailableCampsites(List<Campsite> campsites) {
		for (Campsite campsite : campsites) {
			if (campsite.getAvailable()) {
				System.out.println(campsite.getName());	
			}
		}
	}
	
	public String queryJsonPath() {
		System.out.println("Enter the path to a JSON file");	
		String JsonPath = "";
		try {
		scanner = new Scanner(System.in);
		JsonPath = scanner.next();
		} finally {
			 if (scanner != null) {
			        scanner.close();
			 }
		}
		return JsonPath;
	}
}