package com.spring.cloud.service;

import com.spring.cloud.model.FlightDTO;

public interface FlightService {

	public boolean updateFlightDetails(Long flightId, FlightDTO updatedFlightDto);
	
	public boolean updateAvailableSeats(Long flightId, int availableSeats);
	
	public boolean cancelFlight(Long flightId);
}
