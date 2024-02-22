package com.spring.cloud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.spring.cloud.model.Users;
import com.spring.cloud.dao.UserDao;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {


	@Mock
	private UserDao userRepository;

	@InjectMocks
	private UserService userService;
	
	/*
	 * @BeforeEach void setUp() { MockitoAnnotations.initMocks(this); }
	 */



	
	


}
