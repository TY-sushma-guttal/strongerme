package com.tyss.strongameapp.exception;

public class CoachForSessionDetailsException extends RuntimeException {
	
	String message;

	public CoachForSessionDetailsException(String message) {
		super();
		this.message = message;
	}
	

}
