package com.spring.cloud.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "flightId")
	private Long flightId;
	@Column(name = "flightName")
	private String flightName;
	@Column(name = "source")
	private String source;
	@Column(name = "destination")
	private String destination;
	@Column(name = "dateOfJourney")
	private LocalDate dateOfJourney;
	@Column(name = "availableSeats")
	private Integer availableSeats;
	@Column(name = "ticketPrice")
	private Integer ticketPrice;
	@Column(name = "flightClass")
	private String flightClass;
	//private boolean cancelled;
	public Flight() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Flight(Long flightId, String flightName, String source, String destination, LocalDate dateOfJourney,
			Integer availableSeats, Integer ticketPrice, String flightClass) {
		super();
		this.flightId = flightId;
		this.flightName = flightName;
		this.source = source;
		this.destination = destination;
		this.dateOfJourney = dateOfJourney;
		this.availableSeats = availableSeats;
		this.ticketPrice = ticketPrice;
		this.flightClass = flightClass;
		//this.cancelled = cancelled;
	}
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
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

