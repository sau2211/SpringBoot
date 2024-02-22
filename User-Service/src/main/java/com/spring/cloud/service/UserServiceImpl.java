package com.spring.cloud.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.cloud.dao.UserDao;
import com.spring.cloud.exceptions.RecordAlreadyPresentException;
import com.spring.cloud.model.Users;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	@Override
	public ResponseEntity<?> createUser(Users newUser) {
		// TODO Auto-generated method stub
		Optional<Users>  findUserById= userDao.findById(newUser.getId());
		try {
			if(!findUserById.isPresent())
			{
				userDao.save(newUser);
				return new ResponseEntity<Users>(newUser,HttpStatus.OK);
			}
			else
				throw new RecordAlreadyPresentException("User with Id: " + newUser.getId() + "already exists!!");
			
		}catch(RecordAlreadyPresentException e)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
	}
	
	public String userLogin(String username,String password) {
		Users user = userDao.findByuserName(username);
		if (user != null && user.getUserPassword().equals(password)) {
			String userType = userDao.findUserTypeByUsername(username);
			if ("administrator".equals(userType)) {
				return "Admin Logged in Successfully";
			}else if ("traveller".equals(userType)) {
				return "Traveller Logged in Successfully";
			}
		}
		return "Login Failed";
	}
	
	public String getUserType(Long id) {
		String userType = userDao.findUserTypeByUserId(id);
		if (userType != null) {
			return userType;
		}else {
			return null;
		}
	}

}
