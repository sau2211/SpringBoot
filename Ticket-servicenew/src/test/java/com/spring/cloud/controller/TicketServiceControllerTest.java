package com.spring.cloud.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.spring.cloud.TicketServicenewApplication;
import com.spring.cloud.client.UserFeignClient;
import com.spring.cloud.controller.TicketServiceController;
import com.spring.cloud.model.Flight;
import com.spring.cloud.model.Ticket;
import com.spring.cloud.model.TicketDTO;
import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.dao.TicketDao;
import com.spring.cloud.service.TicketService;
import com.spring.cloud.service.TicketServiceImpl;

@SpringJUnitConfig(TicketServicenewApplication.class)
@SpringBootTest
class TicketServiceControllerTest {

	@InjectMocks
    private TicketServiceController ticketServiceController;

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private FlightDao flightRepository;
    
    @Mock
    private TicketDao ticketRepository;
    
    @Mock
    private TicketServiceImpl ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
    @Test
    public void testFindAll_ForAdminUser() {
        Long userId = 1L;
        when(userFeignClient.checkUserType(userId)).thenReturn("administrator");
        ResponseEntity<?> response = ticketServiceController.findAll(userId);
        verify(userFeignClient, times(1)).checkUserType(userId);
        verifyNoMoreInteractions(userFeignClient);
        verifyZeroInteractions(flightRepository);
        assertSame(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied.", response.getBody());

    }
    
    @Test
    public void testFindAll_ForNonAdminUser() {
        Long userId = 2L;
        when(userFeignClient.checkUserType(userId)).thenReturn("traveller");
        List<Flight> mockFlights = Arrays.asList(
        		new Flight(1L, "TestFlightName1", "TestSource1", "TestDestination1",LocalDate.of(2023, 9, 16), 200, 299, "Business"),
                new Flight(1L, "TestFlightName2", "TestSource2", "TestDestination2",LocalDate.of(2023, 9, 18), 250, 399, "Economy"));
        when(flightRepository.findAll()).thenReturn(mockFlights);
        ResponseEntity<?> response = ticketServiceController.findAll(userId);
        verify(userFeignClient, times(1)).checkUserType(userId);
        verify(flightRepository, times(1)).findAll();
        verifyNoMoreInteractions(userFeignClient, flightRepository);
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(mockFlights, response.getBody());
    }

}
