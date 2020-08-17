package com.example.demo.exceptions;

public class EmailExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailExistException() {
		super("Email already exist in DB! please be more creative...");
	}

}
