package com.example.demo.exceptions;

public class CouponPurchasedBeforException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CouponPurchasedBeforException() {
		super("You already purchase this coupon!");
	}
	
	

}
