package com.spring.cloud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.cloud.model.Flight;

@Repository
public interface FlightDao extends JpaRepository<Flight, Long> {
	public List<Flight> findAll();
}
