package com.promineotech.concertVenueApi.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.concertVenueApi.entity.Customer;
import com.promineotech.concertVenueApi.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<Object> register(@RequestBody Customer cred) {
		try {
			return new ResponseEntity<Object>(customerService.register(cred), HttpStatus.CREATED);
		} catch (AuthenticationException e){
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Customer cred) {
		try {
			return new ResponseEntity<Object>(customerService.login(cred), HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.GET) 
	public ResponseEntity<Object> getCustomer(@PathVariable Long customerId) {
		try {
			return new ResponseEntity<Object>(customerService.getCustomerById(customerId), HttpStatus.OK);	
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getCustomers() {
		return new ResponseEntity<Object>(customerService.getCustomers(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer, @PathVariable Long customerId) {
		try {
			return new ResponseEntity<Object>(customerService.updateCustomer(customer, customerId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{customerId}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId) {
		try {
			customerService.deleteCustomer(customerId);
			return new ResponseEntity<Object>("Successfully deleted customer with id: " + customerId, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
