package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
	
	Optional<Coupon> findCouponByTitle(String title);
	
	public List<Coupon> findCouponsByCompanyId(int id);//used for get company coupons.
	
	public List<Coupon> findCouponsByCompany(Company company);
	
	public boolean existsByTitleAndCompany(String title, Company company);// used for add coupon.
	
	public List<Coupon> findCouponsByCompanyIdAndCategory(int id, CategoryType category); //used for get company coupons by category.
	
	public List<Coupon> findCouponsByCompanyIdAndPriceLessThan(int id, double maxPrice);  //used for get company coupons by max price.
	
	
	@Modifying
	@Transactional
	@Query(value = "select * from coupons where company_id = ?", nativeQuery = true)
	  public List<Coupon> getCouponsByCompanyId(int id);
	
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from customers_coupons where coupons_id = ?", nativeQuery = true)
	  public void deleteCouponsPurchaseByCouponId(int couponId); // used for delete company and delete coupon.
	
	@Modifying
	@Transactional
	@Query(value = "delete from coupons where company_id = ?", nativeQuery = true)
	public void deleteCompanyCoupons(int companyId); // used for delete company.
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from customers_coupons where customers_id = ?", nativeQuery = true)
	  public void deleteCouponsPurchaseByCustomerId(int customerId); // used for delete customer.
	
}
