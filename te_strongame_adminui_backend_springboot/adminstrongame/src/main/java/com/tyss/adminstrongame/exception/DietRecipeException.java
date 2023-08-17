package com.tyss.adminstrongame.exception;

public class DietRecipeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public DietRecipeException(String message) {
		super(message);
		this.message = message;
	}	

}
