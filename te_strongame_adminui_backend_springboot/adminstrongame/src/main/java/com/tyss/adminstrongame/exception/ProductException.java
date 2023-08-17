package com.tyss.adminstrongame.exception;

public class ProductException extends RuntimeException{
	String message;

	public ProductException(String message) {
		super();
		this.message = message;
	}

}
