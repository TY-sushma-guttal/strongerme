package com.tyss.adminstrongame.exception;

public class PlanException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public PlanException(String message) {
		super(message);
		this.message = message;
	}
	
	

}
