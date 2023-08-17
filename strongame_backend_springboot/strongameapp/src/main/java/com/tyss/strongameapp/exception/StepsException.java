package com.tyss.strongameapp.exception;

public class StepsException extends RuntimeException {
	String message;

	public StepsException(String message) {
		super();
		this.message = message;
	}
	

}
