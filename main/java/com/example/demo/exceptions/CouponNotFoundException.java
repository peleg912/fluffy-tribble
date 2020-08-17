package com.example.demo.exceptions;

public class CouponNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CouponNotFoundException() {
		super("coupon not found!");
	}

}
