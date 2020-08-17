package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.beans.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	public boolean existsByPasswordAndEmail(String password, String email); //used for customer login.
	
	public boolean existsByEmail(String email); //used for add customer.
	
	@Query("select c.id from Customer c where c.password = :password and c.email = :email")
	public int findCustomerIdByPasswordAndEmail(String password, String email); //used to save customer id after a successful login..
	
	
	Optional<Customer> findCustomerByEmail(String email);
	

}
