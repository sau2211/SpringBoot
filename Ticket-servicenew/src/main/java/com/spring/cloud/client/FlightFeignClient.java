package com.spring.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name= "flight-service", url = "localhost:8082" )
public interface FlightFeignClient {

	
	@GetMapping("/checkUserType")
    public String checkUserType(@RequestParam("id") Long id);
	
	@GetMapping("/flight-service/findAll")
	public ResponseEntity<?> findAll(@RequestParam("id") Long id);
	
}
