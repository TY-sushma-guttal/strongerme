package com.tyss.strongameapp.exception;

public class SessionException extends RuntimeException{
	
	String message;

	public SessionException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
	
	
	
	

}
