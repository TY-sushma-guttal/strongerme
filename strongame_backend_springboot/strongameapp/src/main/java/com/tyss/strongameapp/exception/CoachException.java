package com.tyss.strongameapp.exception;

public class CoachException extends RuntimeException {

	private String message;

	public CoachException(String message) {
		super();
		this.message = message;
	}
	
}
