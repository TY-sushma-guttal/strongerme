package com.tyss.adminstrongame.exception;

public class SessionException extends RuntimeException{
	
	String message;

	public SessionException(String message) {
		super();
		this.message = message;
	}
	
	

}
