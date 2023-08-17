package com.tyss.strongameapp.exception;

import java.io.IOException;

public class FileStorageException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message ;

	public FileStorageException(String message, IOException ex) {
		super();
		this.message = message;
	}

	public FileStorageException(String message) {
		this.message = message;
	}
	
}
