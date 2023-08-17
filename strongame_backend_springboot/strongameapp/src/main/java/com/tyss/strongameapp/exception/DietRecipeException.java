package com.tyss.strongameapp.exception;

public class DietRecipeException extends RuntimeException {
	String message;

	public DietRecipeException(String message) {
		super();
		this.message = message;
	}
	

}
