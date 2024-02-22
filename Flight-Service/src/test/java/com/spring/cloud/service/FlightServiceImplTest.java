package com.spring.cloud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import org.mockito.ArgumentMatchers;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.cloud.model.Flight;
import com.spring.cloud.model.FlightDTO;
import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.service.FlightServiceImpl;

import org.junit.jupiter.api.Test;

class FlightServiceImplTest {

	 @InjectMocks
	 private FlightServiceImpl flightService;

	    @Mock
	    private FlightDao flightRepository;

	    @BeforeEach
	    public void init() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @Test
	    public void testUpdateFlightDetails_Success() {
	        Long flightId = 1L;
	        FlightDTO updatedFlightDto = new FlightDTO();
	        updatedFlightDto.setFlightName("Updated Flight");
	        updatedFlightDto.setSource("Source City");
	        updatedFlightDto.setDestination("Destination City");
	        updatedFlightDto.setDateOfJourney(LocalDate.of(2023, 9, 20));
	        updatedFlightDto.setAvailableSeats(250);
	        updatedFlightDto.setTicketPrice(199);
	        updatedFlightDto.setFlightClass("Business");
	        
	        Flight existingFlight = new Flight(flightId, "Original Flight", "Old Source", "Old Destination",LocalDate.of(2023, 9, 15),250,199, "Economy");
	        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
	        boolean result = flightService.updateFlightDetails(flightId, updatedFlightDto);
	        assertTrue(result);
	        verify(flightRepository, times(1)).save(existingFlight);

	    }

	    @Test
	    public void testUpdateFlightDetails_FlightNotFound() {
	        Long flightId = 1L;
	        FlightDTO updatedFlightDto = new FlightDTO();
	        updatedFlightDto.setFlightName("Updated Flight");
	        updatedFlightDto.setSource("Source City");
	        updatedFlightDto.setDestination("Destination City");
	        updatedFlightDto.setDateOfJourney(LocalDate.of(2023, 9, 20));
	        updatedFlightDto.setAvailableSeats(250);
	        updatedFlightDto.setTicketPrice(199);
	        updatedFlightDto.setFlightClass("Business");
	        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());

	        boolean result = flightService.updateFlightDetails(flightId, updatedFlightDto);
	        assertFalse(result);
	        verify(flightRepository, never()).save(any());
	    }
	    
	    @Test
	    public void testUpdateAvailableSeats_Success() {
	        Long flightId = 1L;
	        int availableSeats = 100;
	        Flight existingFlight = new Flight(flightId, "Original Flight", "Source City", "Destination City",LocalDate.of(2023, 9, 15),250, 199, "Business");
	        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
	        boolean result = flightService.updateAvailableSeats(flightId, availableSeats);
	        assertTrue(result);
	        verify(flightRepository, times(1)).save(existingFlight);
	    }
	    
	    @Test
	    public void testUpdateAvailableSeats_FlightNotFound() {
	        Long flightId = 1L;
	        int availableSeats = 100;
	        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());
	        boolean result = flightService.updateAvailableSeats(flightId, availableSeats);
	        assertFalse(result);
	        verify(flightRepository, never()).save(any());
	    }
	    

	    @Test
	    public void testCancelFlight_Success() {
	        Long flightId = 1L;
	        Flight existingFlight = new Flight(1L, "Sample Flight", "Source City", "Destination City",LocalDate.of(2023, 9, 20), 250,199, "Business");
	        when(flightRepository.findById(flightId)).thenReturn(Optional.of(existingFlight));
	        boolean result = flightService.cancelFlight(flightId);
	        assertTrue(result);
	        verify(flightRepository, times(1)).delete(existingFlight);
	    }
	    
	    @Test
	    public void testCancelFlight_FlightNotFound() {
	        Long flightId = 1L;
	        when(flightRepository.findById(flightId)).thenReturn(Optional.empty());
	        boolean result = flightService.cancelFlight(flightId);
	        assertFalse(result);
	        verify(flightRepository, never()).delete(any());

	    }
}
