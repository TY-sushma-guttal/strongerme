package com.tyss.adminstrongame.exception;

public class CoachException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public CoachException(String message) {
		super(message);
		this.message = message;
	}
		
}
