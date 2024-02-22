package com.spring.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cloud.client.UserFeignClient;
import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.exception.AccessDeniedException;
import com.spring.cloud.model.Flight;
import com.spring.cloud.model.FlightDTO;
import com.spring.cloud.service.FlightService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class FlightController {

	@Autowired
	private FlightDao flightDao;

	@Autowired
	private FlightService flightService;

	@Autowired
	private UserFeignClient userFeignClient;
	
	@PostMapping("/flight-service/addFlight")
	public ResponseEntity<String> addFlight(@RequestBody FlightDTO flightDTO, @RequestParam Long userId) {

		String userType = userFeignClient.checkUserType(userId);
		if (!userType.equals("administrator")) {
			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			throw new AccessDeniedException("Access denied.");
		}
		Flight flight = new Flight();
		flight.setFlightName(flightDTO.getFlightName());
		flight.setSource(flightDTO.getSource());
		flight.setDestination(flightDTO.getDestination());
		flight.setDateOfJourney(flightDTO.getDateOfJourney());
		flight.setAvailableSeats(flightDTO.getAvailableSeats());
		flight.setTicketPrice(flightDTO.getTicketPrice());
		flight.setFlightClass(flightDTO.getFlightClass());

		flightDao.save(flight);

		return ResponseEntity.ok("Flight added successfully");

	}
	
	@GetMapping("/flight-service/findAll")
	@CircuitBreaker(name = "findAllCircuitBreaker", fallbackMethod = "findAllFallback")
	public ResponseEntity<?> findAll(@RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (!userType.equals("administrator")) {
			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			throw new AccessDeniedException("Access denied.");
		}

		List<Flight> flights = flightDao.findAll();
		return ResponseEntity.ok(flights);
	}
	
	@GetMapping("/flight-service/findBySourceAndDestination/{source}/{destination}")
	public ResponseEntity<?> findBySourceAndDestination(@PathVariable String source, @PathVariable String destination,
			@RequestParam("userId") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (!userType.equals("administrator")) {
			 return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			//throw new AccessDeniedException("Access denied.");

		}
		Flight flight = flightDao.findBySourceAndDestination(source, destination);

		if (flight != null) {
			return ResponseEntity.ok(flight);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@PutMapping("/flight-service/{flightId}/updateFlightDetails")
	public ResponseEntity<String> updateFlightDetails(@PathVariable Long flightId,
			@RequestBody FlightDTO updatedFlightDto, @RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (!userType.equals("administrator")) {
			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			throw new AccessDeniedException("Access denied.");

		}
		if (flightId == null || updatedFlightDto == null) {
			return ResponseEntity.badRequest().body("Invalid input");
		}
		boolean updated = flightService.updateFlightDetails(flightId, updatedFlightDto);
		if (updated) {
			return ResponseEntity.ok("Flight details updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/flight-service/{flightId}/seats")
	public ResponseEntity<String> updateAvailableSeats(@PathVariable Long flightId, @RequestParam int availableSeats,
			@RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (!userType.equals("administrator")) {
			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			throw new AccessDeniedException("Access denied.");

		}

		if (flightId == null || availableSeats < 0) {
			return ResponseEntity.badRequest().body("Invalid input");
		}
		boolean updated = flightService.updateAvailableSeats(flightId, availableSeats);
		if (updated) {
			return ResponseEntity.ok("Available seats updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/flight-service//{flightId}/cancelFlight")
	public ResponseEntity<String> cancelFlight(@PathVariable Long flightId, @RequestParam("id") Long id) {

		String userType = userFeignClient.checkUserType(id);
		if (!userType.equals("administrator")) {
			// return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
			throw new AccessDeniedException("Access denied.");

		}

		if (flightId == null) {
			return ResponseEntity.badRequest().body("Invalid input");
		}
		boolean canceled = flightService.cancelFlight(flightId);
		if (canceled) {
			return ResponseEntity.ok("Flight canceled successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	public ResponseEntity<?> findAllFallback(Long userId, Throwable throwable) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is unavailable.");

	}
	
}
