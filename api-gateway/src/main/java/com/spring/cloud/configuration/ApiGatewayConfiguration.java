package com.spring.cloud.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	// Creating custom route 
	@Bean
	public RouteLocator gatewayRouter1(RouteLocatorBuilder builder) {
	return builder.routes()
	.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
	.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
	.route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
	.route(p -> p.path("/user-service/**").uri("lb://user-service"))
	.route(p -> p.path("/flight-service/**").uri("lb://flight-service"))
	.route(p -> p.path("/ticket-service/**").uri("lb://ticket-servicenew"))
	
	.build();
	}
}
