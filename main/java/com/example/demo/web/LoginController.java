package com.example.demo.web;


import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.loginManager.ClientType;
import com.example.demo.loginManager.LoginManager;
import com.example.demo.services.ClientFacade;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	
	@Autowired
	private Map<String, OurSession> sessions;
	
	private LoginManager manager;
	
	public LoginController(LoginManager manager) {
		super();
		this.manager = manager;
	}


	@PostMapping("/login/{email}/{password}/{type}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType type){
			ClientFacade facade = manager.login(email, password, type);
			if (facade != null) {
			String token = UUID.randomUUID().toString();
			sessions.put(token, new OurSession(facade, System.currentTimeMillis()));
			return ResponseEntity.ok(token);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed! Please check your details...");
			}
	
		
	}
		
		
	
	@PostMapping("/logOut/{token}")
	public ResponseEntity<?> logOut(@PathVariable String token){
			sessions.remove(token);
			return ResponseEntity.ok("See you next time...");
		
	}
	

}
