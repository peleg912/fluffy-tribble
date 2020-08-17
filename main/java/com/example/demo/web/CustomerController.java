package com.example.demo.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.beans.CategoryType;
import com.example.demo.beans.Coupon;
import com.example.demo.exceptions.CouponAmountIsZeroException;

import com.example.demo.exceptions.CouponExpiredException;
import com.example.demo.exceptions.CouponNotFoundException;
import com.example.demo.exceptions.CouponPurchasedBeforException;

import com.example.demo.exceptions.CustomerNotFoundException;

import com.example.demo.services.ClientFacade;

import com.example.demo.services.CustomerFacade;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
	
	@Autowired
	private Map<String, OurSession> sessions;
	
	
	
	@PostMapping("/purchaseCoupon/{token}")
	public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						System.out.println(coupon);
						((CustomerFacade) customer).purchaseCoupon(coupon);
						System.out.println("success!");
						return ResponseEntity.ok(coupon);
					} catch (  CustomerNotFoundException | CouponPurchasedBeforException | CouponAmountIsZeroException | CouponExpiredException e) {
						System.out.println(e.getMessage());
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
	
	@GetMapping("/getCustomerCoupons/{token}")
	public ResponseEntity<?> getCustomerCoupons(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CustomerFacade) customer).getCustomerCoupons());
					} catch (  CustomerNotFoundException e) {
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
	
	@GetMapping("/getCustomerCouponsByCat/{token}/{category}")
	public ResponseEntity<?> getCustomerCouponsByCategory(@PathVariable String token, @PathVariable CategoryType category){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CustomerFacade) customer).getCustomerCouponsByCategory(category));
					} catch (  CustomerNotFoundException e) {
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
	
	
	@GetMapping("/getCustomerCouponsByMax/{token}/{maxPrice}")
	public ResponseEntity<?> getCustomerCouponsByMaxPrice(@PathVariable String token, @PathVariable double maxPrice){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CustomerFacade) customer).getCustomerCouponsByMaxPrice(maxPrice));
					} catch (  CustomerNotFoundException e) {
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
	
	
	@GetMapping("/getCustomerDetails/{token}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((CustomerFacade) customer).getCustomerDetailes());
					} catch (  CustomerNotFoundException e) {
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
	
	
	@GetMapping("/getAllCoupons/{token}")
	public ResponseEntity<?> getAllCoupons(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
						return ResponseEntity.ok(((CustomerFacade) customer).getAllCoupons());	
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	
	@GetMapping("/getOneCoupon/{token}/{id}")
	public ResponseEntity<?> getOneCoupons(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade customer = s.getFacade();
			if( customer instanceof CustomerFacade) {// just in case someone gets into the customer url and he is not the customer!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
						try {
							return ResponseEntity.ok(((CustomerFacade) customer).getOneCoupon(id));
						} catch (CouponNotFoundException e) {
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
