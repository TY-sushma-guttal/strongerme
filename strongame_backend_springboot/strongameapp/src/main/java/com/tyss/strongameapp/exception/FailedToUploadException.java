package com.tyss.strongameapp.exception;

public class FailedToUploadException extends RuntimeException {
	
	String msg ;

	public FailedToUploadException(String msg) {
		super();
		this.msg = msg;
	}
	
	

}
