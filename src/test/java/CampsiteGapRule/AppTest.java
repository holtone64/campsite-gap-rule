package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton, All rights reserved */

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

public class AppTest extends TestCase{
	private CampsiteAvailability campsiteAvailability;
	
	public void testFileRead() throws Exception {
		// attempt to read in our sample JSON file. 
		campsiteAvailability = 
		new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
				new AvailabilityDisplay());
		// make sure the JsonObject is not null or empty -- if it is not, we were able to read some data out of the json file
		assertTrue(campsiteAvailability.getJsonObject() != null);
		assertTrue(!campsiteAvailability.getJsonObject().isEmpty());
	}
	
	public void testAvailability() throws Exception {
		// test to make sure our displayed list of available campsites from the test file is correct
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		List<Long> knownAvailableCampsiteIds = new ArrayList<Long>();
		knownAvailableCampsiteIds.add((long) 5);
		knownAvailableCampsiteIds.add((long) 6);
		knownAvailableCampsiteIds.add((long) 8);
		knownAvailableCampsiteIds.add((long) 9);
		
		List<Campsite> campsites = campsiteAvailability.getCampsites();
		List<Long> availableCampsiteIds = new ArrayList<Long>();
		for (Campsite campsite : campsites) {
			if (campsite.getAvailable()) {
				availableCampsiteIds.add(campsite.getId());
			}
		}
		assertEquals(knownAvailableCampsiteIds, availableCampsiteIds);
	}
	
	public void testCampsiteListPopulation() throws Exception {
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		assertTrue(campsiteAvailability.getCampsites() != null);
		assertTrue(!campsiteAvailability.getCampsites().isEmpty());
	}
	
	public void testCampsiteValidNames() throws Exception {
		// make sure we have valid names so return values make sense to the user
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		List<Campsite> campsites = campsiteAvailability.getCampsites();
		List<Long> availableCampsiteIds = new ArrayList<Long>();
		for (Campsite campsite : campsites) {
			assertTrue(campsite.getName() != null);
			assertTrue(campsite.getName().length() > 0);
			availableCampsiteIds.add(campsite.getId());
		}	
	}
	
	public void testGapRulesAdded() throws Exception {
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		assertTrue(campsiteAvailability.getGapRules() != null);
		assertTrue(campsiteAvailability.getGapRules().size() > 0);
	}
	
	public void testSearchDatesAdded() throws Exception {
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		assertTrue(campsiteAvailability.getSearchDates() != null);
		assertTrue(campsiteAvailability.getSearchDates().size() > 0);
	}
	
	public void testReservationsAdded() throws Exception {
		campsiteAvailability = 
				new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
						new AvailabilityDisplay());
		
		for (Campsite campsite : campsiteAvailability.getCampsites()) {
			assertTrue(campsite.getReservations() != null);
			assertTrue(campsite.getReservations().size() > 0);
		}
	}
}