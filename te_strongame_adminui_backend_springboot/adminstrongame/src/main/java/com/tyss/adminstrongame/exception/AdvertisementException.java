package com.tyss.adminstrongame.exception;

public class AdvertisementException extends RuntimeException{
	String message;

	public AdvertisementException(String message) {
		super();
		this.message = message;
	}

}
