package com.tyss.adminstrongame.exception;

public class SpecializationException extends RuntimeException {
	
	String message;

	public SpecializationException(String message) {
		super();
		this.message = message;
	}
	
}
