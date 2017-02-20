package CampsiteGapRule;

import java.util.ArrayList;
import java.util.List;

public class Campsite {
	private Long id;
	private String name;
	private boolean available;
	private List<Reservation> reservations;
	
	public Campsite() {
		reservations = new ArrayList<Reservation>();
	}
	
	public Campsite(Long id, String name) {
		this.id = id;
		this.name = name;
		reservations = new ArrayList<Reservation>();
	}
	public Campsite(Long id) {
		this.id = id;
		reservations = new ArrayList<Reservation>();
	}
	
	public Campsite(String name) {
		this.name = name;
		reservations = new ArrayList<Reservation>();
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean addReservation(Reservation reservationToAdd) {
		// Check for existing reservation, and if one exists in our target date range, return false.
		// Also return false if our new Reservation's starting date is between the start and end date of each existing reservation,
		// or if the new reservation's start or ending date is the same as
		// make sure the reservation to add  does not start in the middle of an existing reservation, and make sure each existing 
		// reservation does not start in the middle of our reservation to add
		// since we are using LocalDates instead of LocalDateTimes, we can use .equals() for equivalence comparisons
		for (Reservation existingRes : reservations) {
//			if ((reservationToAdd.getStartDate().isAfter(existingRes.getStartDate())) ||
//					(reservationToAdd.getStartDate().equals(existingRes.getStartDate()))) {
//				if ((reservationToAdd.getStartDate().isBefore(existingRes.getEndDate())) ||
//						(reservationToAdd.getStartDate().equals(existingRes.getEndDate()))) {
//					return false;
//				}
//			}
//			if ((existingRes.getStartDate().isAfter(reservationToAdd.getStartDate())) ||
//					(existingRes.getStartDate().equals(reservationToAdd.getStartDate()))) {
//				if ((existingRes.getStartDate().isBefore(reservationToAdd.getEndDate())) ||
//						(existingRes.getStartDate().equals(reservationToAdd.getEndDate()))) {
//					return false;
//				}			
//			}
			if (reservationToAdd.getStartDate().isAfter(existingRes.getStartDate())) {
				if (reservationToAdd.getStartDate().isBefore(existingRes.getEndDate())) {
					return false;
				}
			}
			if (existingRes.getStartDate().isAfter(reservationToAdd.getStartDate())) {
				if (existingRes.getStartDate().isBefore(reservationToAdd.getEndDate())) {
					return false;
				}			
			}
		}
		reservations.add(reservationToAdd);
		return true;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	
	public boolean getAvailable() {
		return available;
	}
	
}