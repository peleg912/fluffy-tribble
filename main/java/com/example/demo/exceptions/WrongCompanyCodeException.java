package com.example.demo.exceptions;

public class WrongCompanyCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongCompanyCodeException() {
		super("The company ID  that you typed OR refered to doesn't match to the current user ID. ");
	}

}
