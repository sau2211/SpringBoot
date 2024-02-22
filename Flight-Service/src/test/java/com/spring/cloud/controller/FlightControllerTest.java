package com.spring.cloud.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.spring.cloud.FlightServiceApplication;
import com.spring.cloud.client.UserFeignClient;
import com.spring.cloud.controller.FlightController;
import com.spring.cloud.exception.AccessDeniedException;
import com.spring.cloud.exception.ResourceNotFoundException;
import com.spring.cloud.model.Flight;
import com.spring.cloud.model.FlightDTO;
import com.spring.cloud.dao.FlightDao;
import com.spring.cloud.service.FlightService;

import org.junit.jupiter.api.Test;


@SpringJUnitConfig(FlightServiceApplication.class)
@SpringBootTest
class FlightControllerTest {

	@InjectMocks
	private FlightController flightServiceController;

	@Mock
	private FlightDao flightRepository;

	@Mock
	private FlightService flightService;

	@Mock
	private UserFeignClient userFeignClient;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testAddFlight() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		FlightDTO flightDTO = new FlightDTO();
		flightDTO.setFlightName("TestFlight");
		flightDTO.setSource("TestSource");
		flightDTO.setDestination("TestDestination");
		flightDTO.setDateOfJourney(LocalDate.of(2023, 9, 15));
		flightDTO.setTicketPrice(250);
		flightDTO.setFlightClass("TestClass");

		Flight savedFlight = new Flight();
		savedFlight.setFlightName("TestFlight");
		savedFlight.setSource("TestSource");
		savedFlight.setDestination("TestDestination");
		savedFlight.setDateOfJourney(LocalDate.of(2023, 9, 15));
		savedFlight.setTicketPrice(250);
		savedFlight.setFlightClass("TestClass");

		when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);
		ResponseEntity<String> response = flightServiceController.addFlight(flightDTO, 1L);

		verify(flightRepository, times(1)).save(any(Flight.class));
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Flight added successfully", response.getBody());

	}

	@Test
	public void testFindAll_AdminRole_Success() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		List<Flight> flights = new ArrayList<>();
		Flight flight = new Flight();
		// flight.setId(1);
		flight.setFlightName("Sample Flight");
		flight.setSource("Source City");
		flight.setDestination("Destination City");
		flight.setDateOfJourney(LocalDate.of(2023, 9, 15));
		flight.setAvailableSeats(150);
		flight.setTicketPrice(199);
		flight.setFlightClass("Economy");
		flights.add(flight);

		when(flightRepository.findAll()).thenReturn(flights);
		ResponseEntity<?> responseEntity = flightServiceController.findAll(1l);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody() instanceof List<?>);
		List<?> responseBody = (List<?>) responseEntity.getBody();
		assertEquals(flights.size(), responseBody.size());
	
}
	
	@Test
	public void testFindAll_NonAdminRole_AccessDeniedException() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("user");
		assertThrows(AccessDeniedException.class, () -> {
			flightServiceController.findAll(1L);
		});
	}	
	
	@Test
	public void testFindBySourceAndDestination_AdminRole_FlightFound() {

		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		String source = "Source City";
		String destination = "Destination City";
		Flight flight = new Flight();
		flight.setFlightId(1L);
		flight.setFlightName("Sample Flight");
		flight.setSource(source);
		flight.setDestination(destination);

		when(flightRepository.findBySourceAndDestination(eq(source), eq(destination))).thenReturn(flight);
		ResponseEntity<?> responseEntity = flightServiceController.findBySourceAndDestination(source, destination, 1L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody() instanceof Flight);
		Flight responseBody = (Flight) responseEntity.getBody();
		assertEquals(flight.getFlightId(), responseBody.getFlightId());
		assertEquals(flight.getFlightName(), responseBody.getFlightName());

	}
	
	@Test
	public void testFindBySourceAndDestination_AdminRole_FlightNotFound() {
		// Arrange
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		String source = "Source City";
		String destination = "Destination City";
		when(flightRepository.findBySourceAndDestination(eq(source), eq(destination))).thenReturn(null);
		// Act
		ResponseEntity<?> responseEntity = flightServiceController.findBySourceAndDestination(source, destination, 1L);
		// Assert
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}
	
	@Test
	public void testUpdateFlightDetails_AdminRole_ValidInput_FlightUpdated() {
		// Arrange
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		Long flightId = 1L;
		FlightDTO updatedFlightDto = new FlightDTO();
		updatedFlightDto.setFlightName("Updated Flight");
		updatedFlightDto.setSource("Updated Source");
		updatedFlightDto.setDestination("Updated Destination");
		updatedFlightDto.setDateOfJourney(LocalDate.of(2023, 9, 16));
		updatedFlightDto.setAvailableSeats(200);
		updatedFlightDto.setTicketPrice(299);
		updatedFlightDto.setFlightClass("Business");
		when(flightService.updateFlightDetails(eq(flightId), eq(updatedFlightDto))).thenReturn(true);
		// Act
		ResponseEntity<String> responseEntity = flightServiceController.updateFlightDetails(flightId, updatedFlightDto,
				1L);
		// Assert
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Flight details updated successfully", responseEntity.getBody());
	}

	@Test
	public void testUpdateFlightDetails_AdminRole_InvalidInput_BadRequest() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		Long flightId = null;
		FlightDTO updatedFlightDto = null;
		ResponseEntity<String> responseEntity = flightServiceController.updateFlightDetails(flightId, updatedFlightDto,
				1L);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Invalid input", responseEntity.getBody());
	}
	
	@Test
	public void testUpdateFlightDetails_AdminRole_FlightNotFound_NotFound() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
		Long flightId = 1L;
		FlightDTO updatedFlightDto = new FlightDTO();
		updatedFlightDto.setFlightName("Updated Flight");
		updatedFlightDto.setSource("Updated Source");
		updatedFlightDto.setDestination("Updated Destination");
		updatedFlightDto.setDateOfJourney(LocalDate.of(2023, 9, 16));
		updatedFlightDto.setAvailableSeats(200);
		updatedFlightDto.setTicketPrice(299);
		updatedFlightDto.setFlightClass("Business");
		when(flightService.updateFlightDetails(eq(flightId), eq(updatedFlightDto))).thenReturn(false); 
		ResponseEntity<String> responseEntity = flightServiceController.updateFlightDetails(flightId, updatedFlightDto,1L);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}


	@Test
	public void testUpdateFlightDetails_NonAdminRole_AccessDeniedException() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("user");
		assertThrows(AccessDeniedException.class, () -> {
			flightServiceController.updateFlightDetails(1L, new FlightDTO("Updated Flight", "Updated Source",
					"Updated Destination", LocalDate.of(2023, 9, 16), 200, 299, "Business"), 1L);
		});
	}
	
	@Test
	public void testUpdateAvailableSeats_AdminRole_ValidInput_SeatsUpdated() {
		when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");

		Long flightId = 1L;
		int availableSeats = 200;
		when(flightService.updateAvailableSeats(eq(flightId), eq(availableSeats))).thenReturn(true);

		ResponseEntity<String> responseEntity = flightServiceController.updateAvailableSeats(flightId, availableSeats,
				1L);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Available seats updated successfully", responseEntity.getBody());
	}
	
	@Test
    public void testUpdateAvailableSeats_AdminRole_InvalidInput_BadRequest() {

        when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
        Long flightId = null;
        int availableSeats = -10; 
        ResponseEntity<String> responseEntity = flightServiceController.updateAvailableSeats(flightId, availableSeats, 1L);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid input", responseEntity.getBody());
    }
	
	@Test
    public void testUpdateAvailableSeats_AdminRole_FlightNotFound_NotFound() {
        when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
        Long flightId = 1L;
        int availableSeats = 200;
        when(flightService.updateAvailableSeats(eq(flightId), eq(availableSeats))).thenReturn(false); 
        ResponseEntity<String> responseEntity = flightServiceController.updateAvailableSeats(flightId, availableSeats, 1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
	
	@Test
    public void testCancelFlight_AdminRole_ValidInput_FlightCanceled() {
        when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
        Long flightId = 1L;
        when(flightService.cancelFlight(eq(flightId))).thenReturn(true);

        ResponseEntity<String> responseEntity = flightServiceController.cancelFlight(flightId, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Flight canceled successfully", responseEntity.getBody());
    }
	
	  @Test
	    public void testCancelFlight_AdminRole_InvalidInput_BadRequest() {
	        when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
	        Long flightId = null; 

	        ResponseEntity<String> responseEntity = flightServiceController.cancelFlight(flightId, 1L);

	        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	        assertEquals("Invalid input", responseEntity.getBody());
	    }
	  
	  @Test
	    public void testCancelFlight_AdminRole_FlightNotCanceled_NotFound() {
	        when(userFeignClient.checkUserType(anyLong())).thenReturn("administrator");
	        Long flightId = 1L;
	        when(flightService.cancelFlight(eq(flightId))).thenReturn(false); 

	        ResponseEntity<String> responseEntity = flightServiceController.cancelFlight(flightId, 1L);

	        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	    }

	    @Test
	    public void testCancelFlight_NonAdminRole_AccessDeniedException() {
	        when(userFeignClient.checkUserType(anyLong())).thenReturn("user");
	        assertThrows(AccessDeniedException.class, () -> {
	            flightServiceController.cancelFlight(1L, 1L);
	        });
	    }
	    
	    @Test
	    public void testFindAllFallback_ServiceUnavailable_ReturnsServiceUnavailableResponse() {
	        ResponseEntity<?> responseEntity = flightServiceController.findAllFallback(1L, new RuntimeException("Service unavailable"));
	        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseEntity.getStatusCode());
	        assertEquals("Service is unavailable.", responseEntity.getBody());
	    }	  
}
