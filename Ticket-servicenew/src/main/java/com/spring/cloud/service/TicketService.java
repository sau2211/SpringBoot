package com.spring.cloud.service;

import com.spring.cloud.model.TicketDTO;

public interface TicketService {
	public boolean updateTicketDetails(Long ticketId, TicketDTO updatedTicketDTO);
	
	public boolean cancelTicket(Long ticketId);
}
