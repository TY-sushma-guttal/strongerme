package com.tyss.adminstrongame.exception;

public class TaglineException extends RuntimeException{
	
	String message;

	public TaglineException(String message) {
		super();
		this.message = message;
	}

}
