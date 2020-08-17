package com.example.demo.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponDateNotValidException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponTitleExistInSameCompanyException;
import com.example.demo.exceptions.IdUpdateException;
import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.exceptions.WrongCompanyCodeException;
import com.example.demo.repositories.CompanyRepo;
import com.example.demo.repositories.CouponRepo;
import com.example.demo.repositories.CustomerRepo;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {

	// This is the ID of the company that is currently connected- after the login it will be saved here.
	private int companyID;
	
   public CompanyFacade(CompanyRepo compr, CustomerRepo custr, CouponRepo coupr) {
		super(compr, custr, coupr);
		
	}

    /**
     * Login to the system- check if the current email and password exists for the same company.
     * If it does- save the id and print welcome! 
     * If not- throw exception and yalla bye :)
     **/
	public boolean login(String email, String password) {
		if (compr.existsByEmailAndPassword(email, password)) {
			this.companyID = compr.findIdByEmailAndPassword(email, password);
			System.out.println("Welcome Company " + this.companyID + "!");
			return true;
		}
		return false;
	}
	/**
	 * Add Coupon -
	 * First of all, make sure that the connected company tries to insert a coupon with the reserved companyID only.
	 * If the title of the new coupon already exists for this company - don't add.
	 * If coupon dates are not valid - don't add.
	 * If all is well - save the coupon to Data Base.
	 **/
	
	public void addCoupon(Coupon coupon) throws CouponTitleExistInSameCompanyException, WrongCompanyCodeException, CouponDateNotValidException {
//		if (coupon.getCompany().getId() == this.companyID) {
			if (coupr.existsByTitleAndCompany(coupon.getTitle(), coupon.getCompany())) {
				throw new CouponTitleExistInSameCompanyException();
			}
			
			Calendar now = Calendar.getInstance();
			if (coupon.getStartDate().before(now.getTime()) || coupon.getEndDate().before(now.getTime()) || coupon.getEndDate().before(coupon.getStartDate())) {
				throw new CouponDateNotValidException();
			}
			
			coupr.save(coupon);
			
//		} else {
//			throw new WrongCompanyCodeException();
//		}
	}
	/**
	 * Update Coupon- 
	 * First, we will pull the original object from Data Base and make sure it exists according to the updated coupon ID.
	 * Then we'll make sure that the coupon they are trying to update belongs to the current company that is connected.
	 * It is not possible to update if the coupon title exists in this company (unless is the same coupon).
	 * If coupon dates are not valid - don't update.
	 * Cannot update companyId or couponId (has to setters). 
	 * If all is well - update (save) the coupon.
	 **/
	public void updateCoupon(Coupon coupon) throws CouponTitleExistInSameCompanyException, CouponNotFoundException, IdUpdateException, WrongCompanyCodeException, CouponDateNotValidException {
		Coupon c = coupr.findById(coupon.getId()).orElseThrow(IdUpdateException::new); //this is the original coupon.
		if (c.getCompany().getId() == this.companyID) {
			Company company = c.getCompany();
			for (Coupon co : company.getCoupons()) {
				if (co.getTitle().equals(coupon.getTitle()) && co.getId() != coupon.getId()) {
					throw new CouponTitleExistInSameCompanyException();
				}
			}
			
			Calendar now = Calendar.getInstance();
			if ( coupon.getEndDate().before(now.getTime())) {
				throw new CouponDateNotValidException();
			}
			coupr.save(coupon);
			
		} else {
			throw new WrongCompanyCodeException();
		}
		
	}

	/**
	 * Delete Coupon-
	 *  First, we will pull the original object from Data Base and make sure it exists.
	 *  We'll make sure the company tries to delete only a coupon that belongs to it.
	 *  Delete the coupon purchase history (from the linked table).
	 *  The coupon itself can now be deleted according to its ID.
	 **/
	public void deleteCoupon(int id) throws WrongCompanyCodeException, CouponNotFoundException {
		Coupon c = coupr.findById(id).orElseThrow(CouponNotFoundException :: new);
		if (c.getCompany().getId() == this.companyID) {
			coupr.deleteCouponsPurchaseByCouponId(id);
			coupr.deleteById(id);
			
		} else {
			throw new WrongCompanyCodeException();
		}
	}
		
	/** Brings the coupons of the company that logged in
	 * @throws CompanyNotFoundException **/
	public List<Coupon> getCompanyCoupons()  {
     	return coupr.findCouponsByCompanyId(companyID);
//		return coupr.getCouponsByCompanyId(companyID);
//		return compr.findById(companyID).orElseThrow(CompanyNotFoundException :: new).getCoupons();
//		Company company  = compr.findById(companyID).orElseThrow(CompanyNotFoundException :: new);
//		return coupr.findCouponsByCompany(company);
	}

	/** Brings the coupons of the company that logged in - by specific category**/
	public List<Coupon> getCompanyCouponsByCategory(CategoryType category) {
		return coupr.findCouponsByCompanyIdAndCategory(companyID, category);
	}

	/** Brings the coupons of the company that logged in - by specific maximum price**/
	public List<Coupon> getCompanyCouponsByMaxPrice(double maxPrice) {
		return coupr.findCouponsByCompanyIdAndPriceLessThan(companyID, maxPrice);
	}

	//**Bring the company itself- Mostly used me for tests**/
	public Company getOneCompany(int id) throws CompanyNotFoundException {
		return compr.findById(id).orElseThrow(CompanyNotFoundException::new);
	}

	/** Bring one specific coupon - Mostly used me for tests
	 * @throws WrongCompanyCodeException **/
	public Coupon getOneCoupon(int id) throws CouponNotFoundException, WrongCompanyCodeException {
		Coupon coupon =  coupr.findById(id).orElseThrow(CouponNotFoundException::new);
		if(coupon.getCompany().getId() == this.companyID) {
			return coupon;
		} else {
			throw new WrongCompanyCodeException();
		}
	}
	
	public Coupon getOneCouponByTitle(String title) throws CouponNotFoundException, WrongCompanyCodeException {
		Coupon coupon =  coupr.findCouponByTitle(title).orElseThrow(CouponNotFoundException :: new);
		if(coupon.getCompany().getId() == this.companyID) {
			return coupon;
		} else {
			throw new WrongCompanyCodeException();
		}
	}

	/**Bring the company details**/
	public Company getCompanyDetails() throws CompanyNotFoundException {
		return compr.findById(companyID).orElseThrow(CompanyNotFoundException::new);

	}
	
	
	
}
