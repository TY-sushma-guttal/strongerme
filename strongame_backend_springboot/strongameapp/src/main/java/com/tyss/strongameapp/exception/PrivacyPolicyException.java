package com.tyss.strongameapp.exception;

public class PrivacyPolicyException extends RuntimeException {

	String message;

	public PrivacyPolicyException(String message) {
		super();
		this.message = message;
	}
	
}
