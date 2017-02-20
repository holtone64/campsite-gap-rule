package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton */

import org.junit.Test;
import org.junit.runner.JUnitCore;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

public class AppTest extends TestCase{
	CampsiteAvailability campsiteAvailability;
	
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
		// make sure the JsonObject is not empty
		assertTrue(!campsiteAvailability.getJsonObject().isEmpty());;
		
	}
	
	public void testCampsiteNames() throws Exception {
		for (Campsite campsite : campsiteAvailability.getCampsites()) {
			assertTrue(!campsite.getName().isEmpty());
		}
	}
	
	
}