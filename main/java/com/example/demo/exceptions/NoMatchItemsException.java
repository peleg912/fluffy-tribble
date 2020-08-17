package com.example.demo.exceptions;

public class NoMatchItemsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoMatchItemsException() {
		super("No items match your request.");
	}

}
