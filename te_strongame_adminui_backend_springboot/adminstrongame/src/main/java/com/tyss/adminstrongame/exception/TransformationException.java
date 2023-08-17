package com.tyss.adminstrongame.exception;

public class TransformationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public TransformationException(String message) {
		super(message);
		this.message = message;
	}
	
}
