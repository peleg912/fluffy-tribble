 package com.example.demo.loginManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.services.AdminFacade;
import com.example.demo.services.ClientFacade;
import com.example.demo.services.CompanyFacade;
import com.example.demo.services.CustomerFacade;

@Service
public class LoginManager {
		
	@Autowired
	private ConfigurableApplicationContext ctx;

	
	public LoginManager() {
		
	}


	public ClientFacade login(String email, String password, ClientType clientType){
		switch  (clientType) {
		
		  case Administrator:
			  AdminFacade admin =  ctx.getBean(AdminFacade.class);
			  if (admin.login(email, password)) {
				  return admin;
			  }
			  break;
			  
		  case Company:
			  CompanyFacade company = ctx.getBean(CompanyFacade.class);
			  if (company.login(email, password)) {
				  return company;
			  }
			  break;
		  case Customer:
			  CustomerFacade customer = ctx.getBean(CustomerFacade.class);
			  if (customer.login(email, password)) {
				  return customer;
			  }
			  break;
		}
		return null;
		
	}

}