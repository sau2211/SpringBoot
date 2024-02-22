package com.spring.cloud.controller;

import java.util.List;
import java.util.Optional;

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

import com.spring.cloud.client.FlightFeignClient;
import com.spring.cloud.client.UserFeignClient;
import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.dao.TicketDao;
import com.spring.cloud.exception.TicketNotFoundException;
import com.spring.cloud.model.Flight;
import com.spring.cloud.model.Ticket;
import com.spring.cloud.model.TicketDTO;
import com.spring.cloud.service.TicketService;

@RestController
public class TicketServiceController {

	@Autowired
	FlightDao flightDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	@Autowired
	private FlightFeignClient flightFeignClient;
	
	@GetMapping("/ticket-service/findAll")
	public ResponseEntity<?> findAll(@RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (userType.equals("administrator")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
		}
		List<Flight> flights = flightDao.findAll();
		return ResponseEntity.ok(flights);
	}
	
	@PostMapping("/ticket-service/bookTicket")
	public ResponseEntity<String> bookTicket(@RequestBody TicketDTO ticketDTO,@RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (userType.equals("administrator")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
		}
		
		Ticket ticket = new Ticket();
		ticket.setFlightId(ticketDTO.getFlightId());
		ticket.setFlightName(ticketDTO.getFlightName());
		ticket.setSource(ticketDTO.getSource());
		ticket.setDestination(ticketDTO.getDestination());
		ticket.setDateOfJourney(ticketDTO.getDateOfJourney());
		ticket.setClassName(ticketDTO.getClassName());
		ticket.setTicketPrice(ticketDTO.getTicketPrice());
		ticket.setTotalPassengers(ticketDTO.getTotalPassengers());
		ticket.setTotalAmount(ticketDTO.getTotalAmount());
		
		ticketDao.save(ticket);
		return ResponseEntity.ok("Ticket Booked successfully");
		
	}
	
	@GetMapping("/ticket-service/findById/{ticketId}")
	public ResponseEntity<?> findById(@PathVariable Long ticketId,@RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (userType.equals("administrator")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
		}
		
		try {
			Optional<Ticket> ticket1 = ticketDao.findById(ticketId);
			Ticket ticket = ticket1.get();
			if (ticket != null) {
				return ResponseEntity.ok(ticket); 
				}else {
					throw new TicketNotFoundException("Ticket not found");
				}
			
		} catch (TicketNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}
	
	@PutMapping("/ticket-service/{ticketId}/updateTicketDetails")
	public ResponseEntity<String> updateTicketDetails(@PathVariable Long ticketId,@RequestBody TicketDTO updatedTicketDTO, @RequestParam("id") Long id) {
		String userType = userFeignClient.checkUserType(id);
		if (userType.equals("administrator")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
		}
		/*
		 * if (ticketId == null || updatedTicketDTO == null) { return
		 * ResponseEntity.badRequest().body("Invalid input"); }
		 */
		boolean updated = ticketService.updateTicketDetails(ticketId, updatedTicketDTO);
		if (updated) {
			return ResponseEntity.ok("Ticket details updated successfully");
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/ticket-service/{ticketId}/cancelTicket")
	public ResponseEntity<String> cancelTicket(@PathVariable Long ticketId,@RequestParam("id") Long id) {
		String userRole = userFeignClient.checkUserType(id);
		if (userRole.equals("administrator")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
		}
		
		boolean canceled = ticketService.cancelTicket(ticketId);
		if (canceled) {
			return ResponseEntity.ok("Ticket canceled successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	

}
