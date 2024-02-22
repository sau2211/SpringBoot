package com.spring.cloud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.cloud.model.Ticket;
import com.spring.cloud.model.TicketDTO;
import com.spring.cloud.dao.TicketDao;
import com.spring.cloud.service.TicketService;

import org.junit.jupiter.api.Test;


class TicketServiceImplTest {

	@InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketDao ticketRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testUpdateTicketDetailsSuccessful() {
        Long ticketId = 1L;
        TicketDTO updatedTicketDTO = new TicketDTO();
        updatedTicketDTO.setFlightId(201L); 
        updatedTicketDTO.setFlightName("Updated Flight"); 
        updatedTicketDTO.setSource("Updated Source"); 
        updatedTicketDTO.setDestination("Updated Destination"); 
        updatedTicketDTO.setDateOfJourney(LocalDate.of(2023,9,15)); 
        updatedTicketDTO.setClassName("Business"); 
        updatedTicketDTO.setTicketPrice(199); 
        updatedTicketDTO.setTotalPassengers(3); 
        updatedTicketDTO.setTotalAmount(599); 
        
        Optional<Ticket> existingTicket1 = Optional.of(new Ticket());
        Ticket existingTicket = existingTicket1.get();
        existingTicket.setTicketId(1L); 
        existingTicket.setFlightId(101L); 
        existingTicket.setFlightName("Existing Flight"); 
        existingTicket.setSource("Existing Source"); 
        existingTicket.setDestination("Existing Destination"); 
        existingTicket.setDateOfJourney(LocalDate.of(2023,9,15)); 
        existingTicket.setClassName("Economy"); 
        existingTicket.setTicketPrice(99.99); 
        existingTicket.setTotalPassengers(2); 
        existingTicket.setTotalAmount(199.98); 
        
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.ofNullable(existingTicket));
        boolean updated = ticketService.updateTicketDetails(ticketId, updatedTicketDTO);
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).save(existingTicket);
        verifyNoMoreInteractions(ticketRepository);
        assertTrue(updated);
    }
    
    @Test
    public void testCancelTicketSuccessful() {
        Long ticketId = 1L;
        Optional<Ticket> existingTicket1 = Optional.of(new Ticket());
        Ticket existingTicket = existingTicket1.get();
        existingTicket.setTicketId(1L); 
        existingTicket.setFlightId(101L); 
        existingTicket.setFlightName("Existing Flight"); 
        existingTicket.setSource("Existing Source"); 
        existingTicket.setDestination("Existing Destination"); 
        existingTicket.setDateOfJourney(LocalDate.of(2023,9,15)); 
        existingTicket.setClassName("Economy"); 
        existingTicket.setTicketPrice(99.99); 
        existingTicket.setTotalPassengers(2); 
        existingTicket.setTotalAmount(199.98); 
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.ofNullable(existingTicket));
        boolean canceled = ticketService.cancelTicket(ticketId);
        verify(ticketRepository, times(1)).findById(ticketId);
        verify(ticketRepository, times(1)).delete(existingTicket);
        verifyNoMoreInteractions(ticketRepository);
        assertTrue(canceled); 
    }
}
