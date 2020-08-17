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

import com.example.demo.beans.Company;
import com.example.demo.beans.Customer;
import com.example.demo.exceptions.CompanyExistException;
import com.example.demo.exceptions.CompanyNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.exceptions.EmailExistException;
import com.example.demo.exceptions.IdUpdateException;
import com.example.demo.exceptions.NameORIdUpdateException;
import com.example.demo.services.AdminFacade;
import com.example.demo.services.ClientFacade;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	
	@Autowired
	private Map<String, OurSession> sessions;
		
	@PostMapping("/addCompany/{token}")
	public ResponseEntity<?> addCompany(@RequestBody Company company, @PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).addCompany(company);
						return ResponseEntity.ok(company);
					} catch (CompanyExistException e) {
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
	
	@PutMapping("/updateCompany/{token}")
	public ResponseEntity<?> updateCompany(@PathVariable String token, @RequestBody Company company){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).updateCompany(company);
						return ResponseEntity.ok(company);
					} catch (NameORIdUpdateException | EmailExistException e) {
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
	
	@DeleteMapping("/deleteCompany/{token}/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).deleteCompany(id);
						return ResponseEntity.ok("Company deleted.");
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
	
	@GetMapping("/getAllComp/{token}")
	public ResponseEntity<?> getAllCompanies(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((AdminFacade) admin).getAllCompanies());
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	@GetMapping("/getOneComp/{token}/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((AdminFacade) admin).getOneCompany(id));
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
	
	@PostMapping("/addCustomer/{token}")
	public ResponseEntity<?> addCustomer(@PathVariable String token, @RequestBody Customer customer){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).addCustomer(customer);
						return ResponseEntity.ok(customer);
					} catch ( EmailExistException e) {
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
	
	
	@PutMapping("/updateCustomer/{token}")
	public ResponseEntity<?> updateCustomer(@PathVariable String token, @RequestBody Customer customer){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).updateCustomer(customer);
						return ResponseEntity.ok(customer);
					} catch ( EmailExistException | IdUpdateException e) {
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
	
	@DeleteMapping("/deleteCustomer/{token}/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						((AdminFacade) admin).deleteCustomer(id);
						return ResponseEntity.ok("customer deleted.");
					} catch (CustomerNotFoundException e) {
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
	
	@GetMapping("/getAllCust/{token}")
	public ResponseEntity<?> getAllCustomers(@PathVariable String token){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					return ResponseEntity.ok(((AdminFacade) admin).getAllCustomers());
				}
				
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed!");
			}
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unautorized login");
		}
		
	}
	
	@GetMapping("/getOneCust/{token}/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((AdminFacade) admin).getOneCustomer(id));
					} catch (CustomerNotFoundException e) {
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
	
	@GetMapping("/getCouponsByComp/{token}/{id}")
	public ResponseEntity<?> getCouponsByCompanyID(@PathVariable String token, @PathVariable int id){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((AdminFacade) admin).getCouponByCompanyId(id));
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
	
	
	@GetMapping("/getCompanyByName/{token}/{name}")
	public ResponseEntity<?> getCompanyByName(@PathVariable String token, @PathVariable String name){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((AdminFacade) admin).getCompanyByName(name));
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
	
	
	@GetMapping("/getCustomerByEmail/{token}/{email}")
	public ResponseEntity<?> getCustomerByEmail(@PathVariable String token, @PathVariable String email ){
		OurSession s = sessions.get(token);
		if (s != null) {
			ClientFacade admin = s.getFacade();
			if( admin instanceof AdminFacade) {// just in case someone gets into the admin url and he is not the admin!
				if (System.currentTimeMillis() - s.getLastAccessed() > 1000*60*30) {
					sessions.remove(token);//if  the client last access was more than 30 minutes logout him and clear his token.
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Go to login!");
				}else {
					s.setLastAccessed(System.currentTimeMillis());
					try {
						return ResponseEntity.ok(((AdminFacade) admin).getCustomerByEmail(email));
					} catch (CustomerNotFoundException e) {
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
