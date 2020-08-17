package com.example.demo.exceptions;

public class CouponTitleExistInSameCompanyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponTitleExistInSameCompanyException() {
		super("This coupon title already exist for this company");
	}

}
