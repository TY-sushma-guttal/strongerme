package com.tyss.strongameapp.exception;

public class NotificationException extends RuntimeException {
	String message;

	public NotificationException(String message) {
		super(message);
	}
	

}
