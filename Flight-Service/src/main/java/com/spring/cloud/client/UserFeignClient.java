package com.spring.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name= "user-service", url = "localhost:8081" )
public interface UserFeignClient {
	@GetMapping("/checkUserType")
	public String checkUserType(@RequestParam("id") Long id );
}
