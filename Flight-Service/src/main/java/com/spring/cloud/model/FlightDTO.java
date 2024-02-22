package com.spring.cloud.model;

import java.time.LocalDate;

public class FlightDTO {
	
	private String flightName;
	private String source;
	private String destination;
	private LocalDate dateOfJourney;
	private Integer availableSeats;
	private Integer ticketPrice;
	private String flightClass;
	public FlightDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FlightDTO(String flightName, String source, String destination, LocalDate dateOfJourney,
			Integer availableSeats, Integer ticketPrice, String flightClass) {
		super();
		this.flightName = flightName;
		this.source = source;
		this.destination = destination;
		this.dateOfJourney = dateOfJourney;
		this.availableSeats = availableSeats;
		this.ticketPrice = ticketPrice;
		this.flightClass = flightClass;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public LocalDate getDateOfJourney() {
		return dateOfJourney;
	}
	public void setDateOfJourney(LocalDate dateOfJourney) {
		this.dateOfJourney = dateOfJourney;
	}
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public Integer getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getFlightClass() {
		return flightClass;
	}
	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}
	
	
}

