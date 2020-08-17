package com.example.demo.repositories;

import java.util.Optional;

//import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.beans.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
	/**
	 All boolean methods check if the conditions are true/false.
	 for exmple:Go to data base and check if there is a company with the specific email and password.
	 **/
	
	/**Used for company login**/
	boolean existsByEmailAndPassword(String email, String password);
	
	/**Used for add company**/
	boolean existsByNameOrEmail(String name, String email);
	

	/**
	 This is a method with HQL.  
	 Go to data base and look for the id of the company with the specific email and password.
	 used to save the company id after a successful login.
	 **/
	@Query("select c.id from Company c where c.email = :email and c.password = :password")
	public int findIdByEmailAndPassword(String email, String password);
	
	
	Optional<Company> findCompanyByName(String name);

}
