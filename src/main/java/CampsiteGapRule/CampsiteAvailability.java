package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton */

/**
 * @author holtone64
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class CampsiteAvailability {
	private JSONObject jsonObj;
	private AvailabilityDisplay availabilityDisplay;
	private List<Campsite> campsites;
	private List<Long> gapRules, invalidatedCampsiteIds;
	private HashMap<String, LocalDate> searchDates;
	
	public CampsiteAvailability(String jsonAsString, AvailabilityDisplay availabilityDisplay) {
		this.availabilityDisplay = availabilityDisplay;
		jsonObj = createSimpleJsonObjectFromString(jsonAsString);
		processData();
	}
	
	public CampsiteAvailability(JSONObject jsonObj, AvailabilityDisplay availabilityDisplay) {
		this.jsonObj = jsonObj;
		this.availabilityDisplay = availabilityDisplay;
		processData();
	}
	
	public CampsiteAvailability(AvailabilityDisplay availabilityDisplay) {
		this.availabilityDisplay = availabilityDisplay;
	}
	
	public List<Campsite> getCampsites() {
		return campsites;
	}
	
	public JSONObject getJsonObject() {
		return jsonObj;
	}
	
	public AvailabilityDisplay getAvailabilityDisplay() {
		return availabilityDisplay;
	}
	
	public void setJsonObject(String jsonPath) {
		jsonObj = readInputFile(jsonPath);
		processData();
	}
	
	public List<String> getAvailableCampsites() {
		List<String> availableCampsiteNames = new ArrayList<String>();
		for (Campsite campsite : campsites) {
			if (campsite.getAvailable()) {
				availableCampsiteNames.add(campsite.getName());	
			}
		}
		return availableCampsiteNames;
	}
	
	public static JSONObject readInputFile(String inputFilePath) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(CampsiteAvailability.class.getResourceAsStream(inputFilePath)));
			Object inputFile = parser.parse(reader);
			jsonObj = (JSONObject) inputFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	public JSONObject createSimpleJsonObjectFromString(String inputString) {
		// convenience method to convert a string to a org.simple.JSONObject. 
		return (JSONObject) JSONValue.parse(inputString);
	}
	
	private void processData() {
		// stop processing if the jsonObj Object is currently empty
		if (jsonObj != null) {
			readCampsites();
			readReservations();
			readGapRules();
			readSearchDates();
			
			// put our new reservation in, run checkInvalidatedCampsites, and set our campsites' availability booleans to false based on the return list
			for (Campsite campsite: campsites) {
				Reservation resevationToAdd = new Reservation();
				resevationToAdd.setStartDate((LocalDate)searchDates.get("startDate"));
				resevationToAdd.setEndDate((LocalDate)searchDates.get("endDate"));
				resevationToAdd.setCampsiteId(campsite.getId());
				resevationToAdd.setExistingReservation(false);
				boolean reservationAdded = campsite.addReservation(resevationToAdd);
				if (!reservationAdded) {
					campsite.setAvailable(false);
				}
			}
			
	        // sort the reservations in each campsite based on our comparable set to the start date
	        List<Reservation> listToSort = new ArrayList<Reservation>();
	    	for (Campsite campsite : campsites) {
	    		listToSort = campsite.getReservations();
	    		Collections.sort(listToSort);
	    		List<String> dates = new ArrayList<String>();
	    		for (Reservation r : listToSort) {
	    			dates.add(r.getStartDate().toString());
	    		}
	    		campsite.setReservations(listToSort);
	    	}
			
			checkInvalidatedCampsites();
			
			for (Campsite campsite : campsites) {
				for (Long campsiteId : invalidatedCampsiteIds) {
					if (campsite.getId() == campsiteId) {
						campsite.setAvailable(false);
					}
				}
			}
			
			//availabilityDisplay.displayAvailableCampsites(campsites);
		} else {
			System.out.println("JSON Object is empty");
		}
	}
	
	private void checkInvalidatedCampsites() {
		// for each gap rule, for each reservation, check whether this reservation's start date minus the 
		// previous reservation's end date == gapRule.  if so, pull campsite id and set campsite's availability boolean to false
		invalidatedCampsiteIds = new ArrayList<Long>();
		for (Long gapSize : gapRules) {
			for (Campsite campsite : campsites) {
				for (int i = 1; i < campsite.getReservations().size(); i++) {
					// don't bother doing date comparisons if we're not looking at a new reservation
					// Need to make sure our daysBetween value represents days between an existing reservation and a new one in addition
					// to matching our gapSize before adding the campsite id to our invalidatedCampsiteIds list. This prohibits
					// false negatives coming from reservation already in the system that violate our gap rules. 
					if (!campsite.getReservations().get(i).getExistingReservation() ||
							!campsite.getReservations().get(i-1).getExistingReservation()) {				
						long daysBetween = ChronoUnit.DAYS.between(campsite.getReservations().get(i -1).getEndDate(), 
								campsite.getReservations().get(i).getStartDate()); 
						if (daysBetween-1 == gapSize) {
							invalidatedCampsiteIds.add(campsite.getId());
						}	
					}
				}
			}
		}
	}
	
	private void readCampsites() {
		// want to make sure our campsite ids match up with the indeces in the campsite list to avoid unnecessary looping later
		
		campsites = new ArrayList<Campsite>();
		
        JSONArray jsonCampsites = (JSONArray) jsonObj.get("campsites");
        Iterator<JSONObject> iterator = jsonCampsites.iterator();
        while (iterator.hasNext()) {
        	Campsite campsite = new Campsite();
        	JSONObject jsonCampsite = (JSONObject)iterator.next();
        	Long id = (Long)jsonCampsite.get("id");
        	String name = (String)jsonCampsite.get("name");
        	campsite.setId(id);
        	campsite.setName(name);
        	// the following boolean will be set to false if we find a gapRule violation
        	campsite.setAvailable(true);
        	campsites.add(campsite);
        }
	}
	
	public void readReservations() {
        JSONArray jsonReservations = (JSONArray) jsonObj.get("reservations");
        Iterator<JSONObject> iterator = jsonReservations.iterator();
        while (iterator.hasNext()) {
        	Reservation reservation = new Reservation();
        	JSONObject jsonReservation = (JSONObject)iterator.next();
        	Long campsiteId = (Long)jsonReservation.get("campsiteId");
        	LocalDate startDate = null;
        	LocalDate endDate = null;
        	try {
        		// if the dates are in the format 'yyyy-mm-dd', we do not need to use the DateTimeFormatter
        		startDate = LocalDate.parse((String)jsonReservation.get("startDate"));
        		endDate = LocalDate.parse((String)jsonReservation.get("endDate"));
        	} catch (DateTimeParseException e) {
        		// catch invalid date formats here
        		e.printStackTrace();
        	}
        	reservation.setStartDate(startDate);
        	reservation.setEndDate(endDate);
        	reservation.setCampsiteId(campsiteId);
        	reservation.setExistingReservation(true);
        	
        	// depending on the size of our campsites list, may need to optimize this to use the campsite ids as indeces to avoid this loop
        	for (Campsite campsite : campsites) {
        		if (campsite.getId().equals(campsiteId)) {
        			campsite.addReservation(reservation);
        		}
        	}
        }
	}
	
	public void readGapRules() {
		gapRules = new ArrayList<Long>();
		JSONArray jsonGapRules = (JSONArray) jsonObj.get("gapRules");
		Iterator<JSONObject> iterator = jsonGapRules.iterator();
        while (iterator.hasNext()) {
        	gapRules.add((Long)iterator.next().get("gapSize"));
        }
	}
	
	public void readSearchDates() {
		searchDates = new HashMap<String, LocalDate>();
		JSONObject jsonSearchDate = (JSONObject)jsonObj.get("search");
    	LocalDate startDate = null;
    	LocalDate endDate = null;
    	try {
    		startDate = LocalDate.parse((String)jsonSearchDate.get("startDate"));
    		endDate = LocalDate.parse((String)jsonSearchDate.get("endDate"));
    	} catch (DateTimeParseException e) {
    		// catch invalid date formats here
    		e.printStackTrace();
    	}
    	searchDates.put("startDate", startDate);
    	searchDates.put("endDate", endDate);
	}
	
}