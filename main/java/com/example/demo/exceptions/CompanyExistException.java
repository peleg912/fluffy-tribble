package com.example.demo.exceptions;

public class CompanyExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CompanyExistException() {
		super("Company name OR email already exist in DB! Due to this we canno't add this company.");
	}
	

}
