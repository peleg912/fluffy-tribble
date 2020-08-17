package com.example.demo.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CouponAmountIsZeroException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponPurchasedBeforException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.repositories.CompanyRepo;
import com.example.demo.repositories.CouponRepo;
import com.example.demo.repositories.CustomerRepo;

@Service
@Scope("prototype")
public class CustomerFacade extends ClientFacade{

	// This is the ID of the customer that is currently connected- after the login it will be saved here.
	private int customerID; 
	
    public CustomerFacade(CompanyRepo compr, CustomerRepo custr, CouponRepo coupr) {
		super(compr, custr, coupr);
	}

     
    /**
     * Login to the system- check if the current email and password exists for the same customer.
     * If it does- save the id and print welcome! 
     * If not- throw exception and yalla bye :)
     **/
	public boolean login(String email, String password)  {
		if (custr.existsByPasswordAndEmail(password, email)) {
			this.customerID = custr.findCustomerIdByPasswordAndEmail(password,email);
			System.out.println("Welcome Customer " + this.customerID + "!");
			return true;
		}
		return false;
	}
	
	/**
	 * Purchase coupon-
	 * The customer must not purchase the same coupon more than once.
	 * The coupon cannot be purchased if its amount is 0.
	 * The coupon cannot be purchased if its expiration date has arrived.
	 * We will check if the customer meets these 3 conditions.
	 * If all goes well we will keep the coupon within the customer's coupon list and update (save) the customer.
	 * Finally, we will lower the coupon inventory in one and update (save) the coupon.
	 * **/
	public void purchaseCoupon(Coupon coupon) throws CustomerNotFoundException, CouponPurchasedBeforException, CouponAmountIsZeroException, CouponExpiredException {
	Customer c = custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
	Date now = new Date(System.currentTimeMillis());
	
	for (Coupon coup : c.getCoupons()) {
		if (coup.getId() == coupon.getId()) {
			throw new CouponPurchasedBeforException();
		    }
	     }
		
		 if(coupon.getAmount() == 0) {
			throw new CouponAmountIsZeroException();
		}else if (coupon.getEndDate().before(now)) {
			throw new CouponExpiredException();
		}else {
			c.getCoupons().add(coupon);
			custr.save(c);
			coupon.setAmount(coupon.getAmount()-1);
			coupr.save(coupon);
		}
	
	}
	
		
	//another option to buy coupon (by coupon id).
	public void purchaseCouponById(int couponId) throws CustomerNotFoundException, CouponPurchasedBeforException, CouponAmountIsZeroException, CouponExpiredException, CouponNotFoundException {
		Customer c = custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
		Coupon coup = coupr.findById(couponId).orElseThrow(CouponNotFoundException :: new);
		Date now = new Date(System.currentTimeMillis());
			if (c.getCoupons().contains(coup)) {
				throw new CouponPurchasedBeforException();
			}else if(coup.getAmount() == 0) {
				throw new CouponAmountIsZeroException();
			}else if (coup.getEndDate().before(now)) {
				throw new CouponExpiredException();
			}else {
				c.getCoupons().add(coup);
				custr.save(c);
				coup.setAmount(coup.getAmount()-1);
				coupr.save(coup);
			}
			
		}
	
	/**Brings customer coupons **/
	public Set<Coupon> getCustomerCoupons() throws CustomerNotFoundException{
		Customer c = custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
		return c.getCoupons();
	}
	
	/**Brings customer coupons by category**/
	public List<Coupon> getCustomerCouponsByCategory(CategoryType category) throws CustomerNotFoundException{
		Customer c = custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
		ArrayList<Coupon> couponsByCat = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getCategory().equals(category)) {
				couponsByCat.add(coup);
			}
		}
		return couponsByCat; 
	}
	
	/**Brings customer coupons by max price**/
	public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) throws CustomerNotFoundException{
		Customer c = custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
		ArrayList<Coupon> couponsByMax = new ArrayList<Coupon>();
		for (Coupon coup : c.getCoupons()) {
			if(coup.getPrice() <= maxPrice) {
				couponsByMax.add(coup);
			}
		}
		return couponsByMax; 
	}
	
	/**Brings customer details include coupons**/
	public Customer getCustomerDetailes() throws CustomerNotFoundException {
		 return custr.findById(customerID).orElseThrow(CustomerNotFoundException :: new);
	
	}
	
	/**Brings one coupon by id - mostly for tests**/
	public Coupon getOneCoupon(int id) throws CouponNotFoundException {
		return coupr.findById(id).orElseThrow(CouponNotFoundException :: new);
	}
	
	/**Brings all coupons**/
	public List<Coupon> getAllCoupons() {
		return coupr.findAll();
	}
	

}
