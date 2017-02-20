package CampsiteGapRule;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	
	public static JSONObject readInputFile(String inputFilePath) {
		// convenience method to create a org.json.simple.JSONObject from input file
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			Object inputFile = parser.parse(new FileReader(inputFilePath));
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
		readCampsites();
		readReservations();
		readGapRules();
		readSearchDates();
		
		// put our new reservation in, run checkGapRules, and set our campsites' availability booleans to false based on the return list
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
        List<Reservation> listToSort = new ArrayList();
    	for (Campsite campsite : campsites) {
    		listToSort = campsite.getReservations();
    		Collections.sort(listToSort);
    		List<String> dates = new ArrayList();
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
		
		for (Campsite campsite : campsites) {
			if (campsite.getAvailable()) {
				System.out.println(campsite.getName());	
			}
		}
	}
	
	private void checkInvalidatedCampsites() {
		// for each gap rule, for each reservation, check whether this reservation's start date minus the 
		// previous reservation's end date == gapRule.  if so, pull campsite id and set campsite's availability boolean to false
		// returns a list of campsit
		invalidatedCampsiteIds = new ArrayList();
		for (Long gapSize : gapRules) {
			for (Campsite campsite : campsites) {
				//for (int i = 1; i < reservations.size(); i++) {
				List<Reservation> yar = campsite.getReservations();
				String name = campsite.getName();
				//Collections.sort(yar);
				List<String> dates = new ArrayList();
				for (Reservation r : yar) {
					dates.add(r.getStartDate().toString());
				}
				//List<String> dates = campsite.getReservations();
				for (int i = 1; i < campsite.getReservations().size(); i++) {
					//long daysBetween = ChronoUnit.DAYS.between(reservations.get(i).getStartDate(), reservations.get(i -1).getEndDate());
					long daysBetween = ChronoUnit.DAYS.between(campsite.getReservations().get(i -1).getEndDate(), 
							campsite.getReservations().get(i).getStartDate());
					List<String> testDates = new ArrayList<String>();
					for (Reservation reservation : campsite.getReservations()) {
						testDates.add(reservation.getStartDate().toString());
					}
					LocalDate yarsdf = campsite.getReservations().get(i).getStartDate();
					String asdfsd = yarsdf.toString();
					LocalDate sdfsadfd = campsite.getReservations().get(i -1).getEndDate();
					String asddsfd = sdfsadfd.toString();
					//long daysBetween = DAYS.between(dateBefore, dateAfter);
					// need to consider campsites in addition to reservations and dates, too -- have to add res to each individual campsites' list of reservations
					//int yar = reservations.get(i).getStartDate().compareTo(reservations.get(i -1).getEndDate());
					//if (reservations.get(i).getStartDate().compareTo(reservations.get(i -1).getEndDate()) == gapSize) {
					//if (ChronoUnit.DAYS.between(reservations.get(i).getStartDate(), reservations.get(i -1).getEndDate()) == gapSize) {
					Long what = -daysBetween-1;
					// Need to make sure our daysBetween value represents days between an existing resevation and a new one in addition
					// to matching our gapSize before adding the campsite id to our invalidatedCampsiteIds list
					if ((daysBetween-1 == gapSize) && (!campsite.getReservations().get(i).getExistingReservation() ||
							!campsite.getReservations().get(i-1).getExistingReservation())) {
						//invalidatedCampsiteIds.add(reservations.get(i).getCampsiteId());
						//invalidatedCampsiteIds.add(campsite.getReservations().get(i).getCampsiteId());
						// want to avoid false positives coming from reservations that were already in the system.
						invalidatedCampsiteIds.add(campsite.getId());
					}				
				}
			}
		}
	}
	
	private void readCampsites() {
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
//		reservations = new ArrayList<Reservation>();
        JSONArray jsonReservations = (JSONArray) jsonObj.get("reservations");
        Iterator<JSONObject> iterator = jsonReservations.iterator();
        while (iterator.hasNext()) {
        	Reservation reservation = new Reservation();
        	JSONObject jsonReservation = (JSONObject)iterator.next();
        	Long campsiteId = (Long)jsonReservation.get("campsiteId");
        	//String startDateString = (String)jsonReservation.get("startDate");
        	//DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        	//LocalDate startDate = new LocalDate();
        	//LocalDate endDate = new LocalDate();
        	LocalDate startDate = null;
        	LocalDate endDate = null;
        	try {
        		//startDate = dateFormat.parse((String)jsonReservation.get("startDate"));
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
		searchDates = new HashMap();
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