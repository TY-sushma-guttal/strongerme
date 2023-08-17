package com.tyss.adminstrongame.exception;

public class NotificationException extends RuntimeException {
	
	String message;

	public NotificationException(String message) {
		super(message);
	}
	

}
