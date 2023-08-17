package com.tyss.strongameapp.exception;

public class SpecializationDetailsException extends RuntimeException {
	
	String message;

	public SpecializationDetailsException(String message) {
		super();
		this.message = message;
	}
	
}
