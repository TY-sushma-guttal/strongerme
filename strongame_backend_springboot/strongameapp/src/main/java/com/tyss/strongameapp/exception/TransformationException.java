package com.tyss.strongameapp.exception;

public class TransformationException extends RuntimeException {
	
	private String message;

	public TransformationException(String message) {
		super();
		this.message = message;
	}
	
	

}
