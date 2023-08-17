package com.tyss.strongameapp.exception;

public class PlanException extends RuntimeException {
	String message;

	public PlanException(String message) {
		super();
		this.message = message;
	}
	

}
