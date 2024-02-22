package com.spring.cloud.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ticketId;
	private Long flightId;
	private  String flightName;
	private String source;
	private String destination;
	private LocalDate dateOfJourney;
	
	private String className;
	private double ticketPrice;
	private int totalPassengers;
	private double totalAmount;
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTotalPassengers() {
		return totalPassengers;
	}
	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Ticket(Long ticketId, Long flightId, String flightName, String source, String destination,
			LocalDate dateOfJourney, String className, double ticketPrice, int totalPassengers, double totalAmount) {
		super();
		this.ticketId = ticketId;
		this.flightId = flightId;
		this.flightName = flightName;
		this.source = source;
		this.destination = destination;
		this.dateOfJourney = dateOfJourney;
		this.className = className;
		this.ticketPrice = ticketPrice;
		this.totalPassengers = totalPassengers;
		this.totalAmount = totalAmount;
	}
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Ticket [ticketId=" + ticketId + ", flightId=" + flightId + ", flightName=" + flightName + ", source="
				+ source + ", destination=" + destination + ", dateOfJourney=" + dateOfJourney + ", className="
				+ className + ", ticketPrice=" + ticketPrice + ", totalPassengers=" + totalPassengers + ", totalAmount="
				+ totalAmount + "]";
	}
	
	
}
