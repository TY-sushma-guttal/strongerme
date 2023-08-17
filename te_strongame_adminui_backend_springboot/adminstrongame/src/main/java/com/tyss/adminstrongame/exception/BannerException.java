package com.tyss.adminstrongame.exception;

public class BannerException extends RuntimeException{

	String message;

	public BannerException(String message) {
		super(message);
		this.message = message;
	}

}
