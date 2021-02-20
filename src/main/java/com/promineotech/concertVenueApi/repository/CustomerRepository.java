package com.promineotech.concertVenueApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.concertVenueApi.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	
	public Customer findByUsername(String username);

}
