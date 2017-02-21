package CampsiteGapRule;

/* Copyright (c) 2017 Eric Holton, All rights reserved */

/**
 * @author holtone64
 *
 */

import java.time.LocalDate;
import java.util.Date;

public class Reservation implements Comparable<Reservation> {
	private Long campsiteId;
	private LocalDate startDate, endDate;
	private boolean existingReservation;
	
	public Reservation(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Reservation(){}
	
	public Reservation (LocalDate startDate, LocalDate endDate, Long campsiteId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.campsiteId = campsiteId;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public void setCampsiteId(Long campsiteId) {
		this.campsiteId = campsiteId;
	}
	
	public void setExistingReservation(boolean existingReservation) {
		this.existingReservation = existingReservation;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public Long getCampsiteId() {
		return campsiteId;
	}
	
	public boolean getExistingReservation() {
		return existingReservation;
	}
	
	public int compareTo(Reservation reservation) {
		return startDate.compareTo(reservation.getStartDate());
	}
}