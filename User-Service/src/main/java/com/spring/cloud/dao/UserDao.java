package com.spring.cloud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.cloud.model.Users;
@Repository
public interface UserDao extends JpaRepository<Users,Long>{

	public Users findByuserName(String userName);
	
	@Query("SELECT u.userType FROM Users u WHERE u.userName = :username")
	public String findUserTypeByUsername(@Param("username") String username);
	
	@Query("SELECT u.userType FROM Users u WHERE u.id = :id")
	public String findUserTypeByUserId(@Param("id") Long id);
	
}
