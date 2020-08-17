package com.example.demo.exceptions;

public class CouponExpiredException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CouponExpiredException() {
		super("This coupon date is expired!");
	}

}
