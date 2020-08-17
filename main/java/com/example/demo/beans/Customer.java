package com.example.demo.beans;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customers")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String email;
	@Column
	private String password;
	@ManyToMany (fetch = FetchType.EAGER)
	private Set<Coupon> coupons;
	
	
	public Customer() {
	}


	public Customer(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getId() {
		return id;
	}


	public Set<Coupon> getCoupons() {
		return coupons;
	}

	

	@Override
	public int hashCode() {
		return (id * 18 + (email.hashCode()) + (firstName.hashCode()) + (lastName.hashCode()));
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer) {
			Customer c = (Customer) obj;
			if(c.id == this.id && c.email.equals(this.email)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}
	
	
	
	
	
	
	
	
	
	

	
}
