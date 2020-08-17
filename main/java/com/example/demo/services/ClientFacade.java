package com.example.demo.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.repositories.CompanyRepo;
import com.example.demo.repositories.CouponRepo;
import com.example.demo.repositories.CustomerRepo;

@Service
@Scope("prototype")
public abstract class ClientFacade {
	
	protected CompanyRepo compr;
	protected CustomerRepo custr;
	protected CouponRepo coupr;
	
	
	//Instead of autowired we have constructor.
	public ClientFacade(CompanyRepo compr, CustomerRepo custr, CouponRepo coupr) {
		super();
		this.compr = compr;
		this.custr = custr;
		this.coupr = coupr;
	}


   // this method will be implement in all facades.
	public abstract boolean login(String password, String email);
}
