package com.tyss.strongameapp.exception;

public class PlaceOrderException extends RuntimeException {

	String message;

	public PlaceOrderException(String message) {
		super();
		this.message = message;
	}
	
}
