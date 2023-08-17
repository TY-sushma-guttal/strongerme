package com.tyss.adminstrongame.exception;

public class CoachForSessionException extends RuntimeException {
	
	String message;

	public CoachForSessionException(String message) {
		super();
		this.message = message;
	}
	

}
