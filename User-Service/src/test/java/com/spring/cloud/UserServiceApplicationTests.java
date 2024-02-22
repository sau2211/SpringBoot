package com.spring.cloud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.spring.cloud.controller.UserController;
import com.spring.cloud.model.UserDTO;
import com.spring.cloud.model.Users;
import com.spring.cloud.service.UserService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	// @SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testLogin() {
		String username = "testUser";
		String password = "password";

		when(userService.userLogin(username, password)).thenReturn("Login successful");
		String response = userController.login(username, password);
		assertEquals("Login successful", response);
	}
	
	@Test
	public void testCheckUserType() {
		long userId =  7;
		when(userService.getUserType(userId)).thenReturn("ROLE_USER");
		String response = userController.checkUserType(userId);
		assertEquals("ROLE_USER", response);

	}

	@Test
	public void testCheckUserTypeUserNotFound() {
		long userId = 2;
		when(userService.getUserType(userId)).thenReturn(null);
		String response = userController.checkUserType(userId);
		assertEquals("User not found", response);
	}

}
