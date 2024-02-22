package com.spring.cloud.service;

import org.springframework.http.ResponseEntity;

import com.spring.cloud.model.Users;

public interface UserService {

	public ResponseEntity<?> createUser(Users newUser);
	
	public String userLogin(String username,String password);
	
	public String getUserType(Long id);
}
