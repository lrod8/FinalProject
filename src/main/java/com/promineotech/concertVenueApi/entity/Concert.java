package com.promineotech.concertVenueApi.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Concert {
	
	private Long concertId;
	private String name;
	private String description;
	private double feePrice;
	
	@JsonIgnore
	private Set<Order> orders;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getConcertId() {
		return concertId;
	}
	
	public void setConcertId(Long concertId) {
		this.concertId = concertId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return feePrice;
	}
	
	public void setPrice(double price) {
		this.feePrice = price;
	}
	
	@ManyToMany
	@JoinTable(name = "concert_order",
		joinColumns = @JoinColumn(name = "orderId"), 
		inverseJoinColumns = @JoinColumn(name = "concertId"))
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
