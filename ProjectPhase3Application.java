package com.example.demo;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponAmountIsZeroException;
import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponPurchasedBeforException;
import com.example.demo.exceptions.CustomerNotFoundException;

import com.example.demo.loginManager.LoginManager;
import com.example.demo.services.AdminFacade;
import com.example.demo.services.CustomerFacade;
import com.example.demo.threads.CouponExpirationDailyJob;

@SpringBootApplication
public class ProjectPhase3Application {

	public static void main(String[] args)  {
		ConfigurableApplicationContext ctx = SpringApplication.run(ProjectPhase3Application.class, args);


		CouponExpirationDailyJob t = ctx.getBean(CouponExpirationDailyJob.class);		
			t.start();
		
		

	}

}
