package com.tyss.strongameapp.exception;

import java.io.FileNotFoundException;

public class MyFileNotFoundException extends FileNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String message;

	public MyFileNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
