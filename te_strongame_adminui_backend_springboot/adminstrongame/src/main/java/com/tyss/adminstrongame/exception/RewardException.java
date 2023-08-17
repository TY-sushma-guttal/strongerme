package com.tyss.adminstrongame.exception;

public class RewardException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public RewardException(String message) {
		super(message);
		this.message = message;
	}	

}
