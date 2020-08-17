package com.example.demo.beans;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Company company;
	@JsonIgnore
	@ManyToMany(mappedBy = "coupons", fetch = FetchType.EAGER)
	private Set<Customer> customers;
	@Column
	private CategoryType category;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private int amount;
	@Column
	private double price;
	@Column
	private String image;
	
	public Coupon() {
	}

	public Coupon(Company company, CategoryType category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.company = company;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}
	
	
	public Set<Customer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coupon) {
			Coupon c = (Coupon) obj;
			if(c.id == this.id && c.title.equals(this.title)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (id * 17 + (title.hashCode()) + (startDate.hashCode()) + (endDate.hashCode()));
	}


	@Override
	public String toString() {
		return "Coupon [id=" + id +  ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}
	
	
	

}
