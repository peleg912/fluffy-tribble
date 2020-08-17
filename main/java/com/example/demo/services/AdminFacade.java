package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.EmailExistException;
import com.example.demo.exceptions.IdUpdateException;
import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.exceptions.NameORIdUpdateException;
import com.example.demo.repositories.CompanyRepo;
import com.example.demo.repositories.CouponRepo;
import com.example.demo.repositories.CustomerRepo;

@Service
@Scope("prototype")
public class AdminFacade extends ClientFacade {


	public AdminFacade(CompanyRepo compr, CustomerRepo custr, CouponRepo coupr) {
		super(compr, custr, coupr);
	}

	 /**
     * Login to the system- check if the current email and password equals to "admin@admin.com" and "admin" (hard coded).
     * If it does-  print welcome! and return true.
     * If not- throw exception and yalla bye :)
     **/
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			System.out.println("Welcome Admin!");
			return true;
		}
//		throw new LoginFailedException();
		return false;
	}

	/**
	 * Add new company-
	 * Not allowed to add new company with the same name or email as others.
	 * We'll check if the database already has a company with the same name or email as the new one.
	 * If it does have - throw exception.
	 * Else- save the new company to the data base.
	 **/
	public void addCompany(Company company) throws CompanyExistException {
		if (compr.existsByNameOrEmail(company.getName(), company.getEmail())) {
			throw new CompanyExistException();
		}
		compr.save(company);

	}

	/**
	 * Update company-
	 * Not allowed to update company id ( no set id).
	 * Not allowed to update company name at all.
	 * Not allowed to update to an existing email.
	 * We'll check if the database already has a company with the same name or email as the new one.
	 * First, we will pull the original object from the database (with the company ID).
	 * We will now compare the name of the original company with the name of the updated company to see if there has been a change.
	 * Now let's run in a loop and see if the email has been updated to an email that already exists for another company.
	 * 
	 **/
	public void updateCompany(Company company) throws NameORIdUpdateException, EmailExistException {
		Company c = compr.findById(company.getId()).orElseThrow(NameORIdUpdateException :: new);
		if (c.getName().equals(company.getName())) {
		 for (Company com : compr.findAll()) {
			if(com.getEmail().equals(company.getEmail()) && com.getId() != company.getId()) {
				throw new EmailExistException();
		      }
				
			} 
		 
		 	compr.save(company);
		 	return;
			
		} throw new NameORIdUpdateException();
	}


	/**
	 * Delete Company-
	 * First, make sure that the company exists by the given id.
	 * Then we will loop over the company's coupons and delete the purchase history of these coupons.
	 * Then we will delete the coupons themselves.
	 * Only at the end can the company itself be deleted.
	 * **/
	public void deleteCompany(int id) throws CompanyNotFoundException {
		Company company = compr.findById(id).orElseThrow(CompanyNotFoundException :: new);
			for (Coupon coup : company.getCoupons()) {
				coupr.deleteCouponsPurchaseByCouponId(coup.getId());
			}
			coupr.deleteCompanyCoupons(id);
			compr.deleteById(id);
		
		}

	
	/**Brings all companies including their coupons**/
	public List<Company> getAllCompanies() {
		return compr.findAll();
	}

	/**Brings one company by id including her coupons**/
	public Company getOneCompany(int id) throws CompanyNotFoundException {
		return compr.findById(id).orElseThrow(CompanyNotFoundException::new);
	}

	/**
	 * Add customer-
	 * Not allowed to add a customer with the same email to an existing customer.
	 * First, we will check if there is an identical email in Data Base.
	 * If all goes well we will save the new customer.
	 **/
	public void addCustomer(Customer customer) throws EmailExistException {
		if (custr.existsByEmail(customer.getEmail())) {
			throw new EmailExistException();
		}
		custr.save(customer);
	}

	/**
	 * Update Customer-
	 * Not allowed to update customer Id (no set id) .
	 * Not allowed to update a customer with the same email to an existing customer.
	 * First, we will pull the original object with the help of the updated coupon ID.
	 * Then we will check that the email has not been updated to an exists email. 
	 * If all goes well we will update (save) the updated customer.
	 * **/
	public void updateCustomer(Customer customer) throws IdUpdateException, EmailExistException {
		Customer c = custr.findById(customer.getId()).orElseThrow(IdUpdateException :: new);
		for (Customer cust : custr.findAll()) {
			if(cust.getEmail().equals(customer.getEmail()) && cust.getId() != c.getId()) {
				throw new EmailExistException();
		}
      }
		custr.save(customer);
	
	}
	
	/**
	 * Delete Customer-
	 * First, make sure that the customer who wants to delete exists in the system.
     * Now we will delete the customer purchase history according to his ID.
     * Finally, we can delete the customer itself.
     * **/
	public void deleteCustomer(int id) throws CustomerNotFoundException {
		if (custr.existsById(id)) {
		coupr.deleteCouponsPurchaseByCustomerId(id);
		custr.deleteById(id);
		return;
		} 
		throw new CustomerNotFoundException();
	}

	/**Brings all customers including their coupons**/
	public List<Customer> getAllCustomers() {
		return custr.findAll();
	}


	/**Brings one customer by id including his coupons**/
	public Customer getOneCustomer(int id) throws CustomerNotFoundException {
		return custr.findById(id).orElseThrow(CustomerNotFoundException::new);

	}
	
	public List<Coupon> getCouponByCompanyId(int companyId) throws CompanyNotFoundException {
		Company company = compr.findById(companyId).orElseThrow(CompanyNotFoundException :: new);
		return company.getCoupons();
	}

	public Company getCompanyByName(String name) throws CompanyNotFoundException {
		return compr.findCompanyByName(name).orElseThrow(CompanyNotFoundException::new);
	}
	
	
	public Customer getCustomerByEmail(String email) throws CustomerNotFoundException {
		return custr.findCustomerByEmail(email).orElseThrow(CustomerNotFoundException::new);
	}
}
