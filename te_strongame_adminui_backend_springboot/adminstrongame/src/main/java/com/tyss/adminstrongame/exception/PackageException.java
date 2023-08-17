package com.tyss.adminstrongame.exception;

public class PackageException extends RuntimeException{
	
	String message;

	public PackageException(String message) {
		super();
		this.message = message;
	}
	
	

}
