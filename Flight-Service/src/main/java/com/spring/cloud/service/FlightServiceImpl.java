package com.spring.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.model.FlightDTO;
import com.spring.cloud.model.Flight;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightDao flightDao;
	
	@Override
	 public boolean updateFlightDetails(Long flightId, FlightDTO updatedFlightDto) {
        Flight existingFlight = flightDao.findById(flightId).orElse(null);
        if (existingFlight != null) {
        	existingFlight.setFlightName(updatedFlightDto.getFlightName());
            existingFlight.setSource(updatedFlightDto.getSource());
            existingFlight.setDestination(updatedFlightDto.getDestination());
            existingFlight.setDateOfJourney(updatedFlightDto.getDateOfJourney());
            existingFlight.setTicketPrice(updatedFlightDto.getTicketPrice());
            existingFlight.setFlightClass(updatedFlightDto.getFlightClass());
            
            flightDao.save(existingFlight);
            return true;
        }
        return false; 
    }

	@Override
	 public boolean updateAvailableSeats(Long flightId, int availableSeats) {
        Flight existingFlight = flightDao.findById(flightId).orElse(null);
        if (existingFlight != null) {
            existingFlight.setAvailableSeats(availableSeats);
            flightDao.save(existingFlight);
            return true;
        }
        return false; 
    }

	@Override
	 public boolean cancelFlight(Long flightId) {
        Flight existingFlight = flightDao.findById(flightId).orElse(null);
        if (existingFlight != null) {
            
            flightDao.delete(existingFlight);
            return true;
        }
        return false; 
    }

}
