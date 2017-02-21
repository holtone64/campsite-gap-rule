package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton */

import org.junit.Test;
import org.junit.runner.JUnitCore;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

public class AppTest extends TestCase{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private CampsiteAvailability campsiteAvailability;
	
//	@Test
//	public static void main(String[] args) throws Exception {                    
//	       JUnitCore.main("CampsiteGapRule"); 
//	       
//			CampsiteAvailability campsiteAvailability = 
//					new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
//							new AvailabilityDisplay());
//	}
	
	public void testFileRead() throws Exception {
		// attempt to read in our sample JSON file. 
		campsiteAvailability = 
		new CampsiteAvailability(CampsiteAvailability.readInputFile("/test-case.json"),
				new AvailabilityDisplay());
		// make sure the JsonObject is not empty -- if it is not, we were able to read some data out of the json file
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
	
	
	
	
}