package com.example.demo.threads;

import java.util.Calendar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Coupon;
import com.example.demo.repositories.CouponRepo;

@Service
public class CouponExpirationDailyJob extends Thread{

	@Autowired
	private CouponRepo cr;
	private boolean quit = false;
	
	public CouponExpirationDailyJob() {
	}
	
	public CouponExpirationDailyJob(CouponRepo cr) {
		super();
		this.cr = cr;
	}



	@Override
	public void run() {
		 while(!quit){
			 int counter = 0;
			// System.out.println("job running!");
			 Calendar now = Calendar.getInstance();
			 for (Coupon c : cr.findAll()) {
				 if (c.getEndDate().before(now.getTime())) {
					 cr.deleteCouponsPurchaseByCouponId(c.getId());
					 cr.deleteById(c.getId());
					 counter ++;
				 }
				
			}
			 System.out.println(counter + " Coupons deleted due to expiration.");
			 
			 try {
		            //the thread sleeps for one day
					sleep(1000 * 60 * 60 * 60 * 24);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
				}
			
		} // while end
		 
		// System.out.println("Job stopping...");
	}
	
	
	public void stopJob() {
		quit = true;
		try {
			sleep(3000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		interrupt();

	}
	
	
	
	
	
}
