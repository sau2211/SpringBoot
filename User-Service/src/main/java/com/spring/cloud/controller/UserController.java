package com.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cloud.model.Users;
import com.spring.cloud.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/createUser")
	public void addUser(@RequestBody Users newUser)
	{
		userService.createUser(newUser);
	}
	
	@GetMapping(value ="/user-service/login/{username}/{password}")
	public String login(@PathVariable String username,@PathVariable String password) {
		return userService.userLogin(username, password);
	}
	
	@GetMapping("/checkUserType")
	public String checkUserType(@RequestParam("id") Long id ) {
		String userType = userService.getUserType(id);
		if (userType != null) {
			return userType;
		}else {
			return "User not found";
		}
	}
}
