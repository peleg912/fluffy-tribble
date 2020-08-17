package com.example.demo.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Company;
import com.example.demo.beans.Coupon;

import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CouponDateNotValidException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponTitleExistInSameCompanyException;
import com.example.demo.exceptions.IdUpdateException;
import com.example.demo.exceptions.WrongCompanyCodeException;
import com.example.demo.services.ClientFacade;
import com.example.demo.services.CompanyFacade;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {
	
	@Autowired
	private Map<String, OurSession> sessions;
	
	@PostMapping("/addCoupon/{token}")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((CompanyFacade) company).addCoupon(coupon);
						return ResponseEntity.ok(company);
					} catch ( CouponTitleExistInSameCompanyException | WrongCompanyCodeException | CouponDateNotValidException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
						
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@PutMapping("/updateCoupon/{token}")
	public ResponseEntity<?> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((CompanyFacade) company).updateCoupon(coupon);
						return ResponseEntity.ok(company);
					} catch ( CouponTitleExistInSameCompanyException | WrongCompanyCodeException | CouponDateNotValidException | CouponNotFoundException | IdUpdateException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
						
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@DeleteMapping("/deleteCoupon/{token}/{id}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String token, @PathVariable int id ){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((CompanyFacade) company).deleteCoupon(id);
						return ResponseEntity.ok("Coupon deleted.");
					} catch ( WrongCompanyCodeException | CouponNotFoundException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
						
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@GetMapping("/getCompanyCoupons/{token}")
	public ResponseEntity<?> getCompanyCoupons(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					System.out.println(((CompanyFacade) company).getCompanyCoupons());
					return ResponseEntity.ok(((CompanyFacade) company).getCompanyCoupons());
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	@GetMapping("/getCompanyCouponsByCat/{token}/{category}")
	public ResponseEntity<?> getCompanyCouponsByCategory( @PathVariable String token, @PathVariable CategoryType category){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade) company).getCompanyCouponsByCategory(category));
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	@GetMapping("/getCompanyCouponsByMax/{token}/{maxPrice}")
	public ResponseEntity<?> getCompanyCouponsByMaxPrice(@PathVariable String token, @PathVariable double maxPrice){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((CompanyFacade) company).getCompanyCouponsByMaxPrice(maxPrice));
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@GetMapping("/getCompanyDetails/{token}")
	public ResponseEntity<?> getCompanyDetailes(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CompanyFacade) company).getCompanyDetails());
					} catch (CompanyNotFoundException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@GetMapping("/getOneCoupon/{token}/{id}")
	public ResponseEntity<?> getOneCoupon(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CompanyFacade) company).getOneCoupon(id));
					} catch (CouponNotFoundException | WrongCompanyCodeException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	

	@GetMapping("/getCouponByTitle/{token}/{title}")
	public ResponseEntity<?> getCouponByTitle(@PathVariable String token, @PathVariable String title){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade company = s.getFacade();
			if( company instanceof CompanyFacade) {// just in case someone gets into the company url and he is not the company!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CompanyFacade) company).getOneCouponByTitle(title));
					} catch (CouponNotFoundException | WrongCompanyCodeException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
					}
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	

}
