package com.tyss.adminstrongame.exception;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetails {
	
	private boolean error;	
	private transient Object data;
	private Date timestamp;
	private String message;
	private String details;
	
	public ErrorDetails(boolean error, Object data, Date timestamp, String message, String details) {
		super();
		this.error = error;
		this.data = data;
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
		
}
