package com.spring.cloud.exception;

public class TicketNotFoundException extends RuntimeException{
	public TicketNotFoundException(String message) {
		super(message); 
	}
	
	
}
