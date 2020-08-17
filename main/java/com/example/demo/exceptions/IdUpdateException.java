package com.example.demo.exceptions;

public class IdUpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IdUpdateException() {
		super("Id is not updateable.");
	}

}
