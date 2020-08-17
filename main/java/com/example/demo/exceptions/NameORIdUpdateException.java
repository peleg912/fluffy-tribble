package com.example.demo.exceptions;

public class NameORIdUpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NameORIdUpdateException() {
		super("Name & ID are not updateable!");
	}

}
