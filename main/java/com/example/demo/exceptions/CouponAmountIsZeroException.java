package com.example.demo.exceptions;

public class CouponAmountIsZeroException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CouponAmountIsZeroException() {
		super("This coupon is  currently not availble on stock!");
	}

}
