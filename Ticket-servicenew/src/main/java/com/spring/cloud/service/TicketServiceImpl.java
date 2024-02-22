package com.spring.cloud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cloud.dao.TicketDao;
import com.spring.cloud.model.Ticket;
import com.spring.cloud.model.TicketDTO;

@Service
public class TicketServiceImpl implements TicketService {

	
	@Autowired
	private TicketDao ticketDao;
	@Override
	public boolean updateTicketDetails(Long ticketId, TicketDTO updatedTicketDTO) {
		Optional<Ticket> existingTicket1 = ticketDao.findById(ticketId);
		
		Ticket existingTicket= existingTicket1.get();
		if (existingTicket != null) {
			existingTicket.setFlightId(updatedTicketDTO.getFlightId());
			existingTicket.setFlightName(updatedTicketDTO.getFlightName());
			existingTicket.setSource(updatedTicketDTO.getSource());
			existingTicket.setDestination(updatedTicketDTO.getDestination());
			existingTicket.setDateOfJourney(updatedTicketDTO.getDateOfJourney());
			existingTicket.setClassName(updatedTicketDTO.getClassName());
			existingTicket.setTicketPrice(updatedTicketDTO.getTicketPrice());
			existingTicket.setTotalPassengers(updatedTicketDTO.getTotalPassengers());
			existingTicket.setTotalAmount(updatedTicketDTO.getTotalAmount());
			
			ticketDao.save(existingTicket);
			return true;
		}
		return false;
	}
	

	@Override
	public boolean cancelTicket(Long ticketId) {
		Optional<Ticket> existingTicket1 = ticketDao.findById(ticketId);
		Ticket existingTicket = existingTicket1.get();
		if (existingTicket != null) {
			ticketDao.delete(existingTicket);
			return true;
		}
		return false;
	}

}
