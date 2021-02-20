package com.promineotech.concertVenueApi.service;

import javax.naming.AuthenticationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.promineotech.concertVenueApi.entity.Customer;
import com.promineotech.concertVenueApi.repository.CustomerRepository;



@Service
public class CustomerService {
	
	private static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer register(Customer cred) throws AuthenticationException {
		Customer customer = new Customer();
		customer.setUsername(cred.getUsername());
		customer.setHash(BCrypt.hashpw(cred.getPassword(), BCrypt.gensalt()));
		customer.setAddress(cred.getAddress());
		customer.setFirstName(cred.getFirstName());
		customer.setLastName(cred.getLastName());
		customer.setLevel(cred.getLevel());
		try {
			return customerRepository.save(customer);
		} catch (DataIntegrityViolationException e) {
			throw new AuthenticationException("Username not available."); 
		}
	}
	
	public Customer login(Customer cred) throws AuthenticationException {
		Customer foundCustomer = customerRepository.findByUsername(cred.getUsername());
		if (foundCustomer != null && BCrypt.checkpw(cred.getPassword(), foundCustomer.getHash())) {
			return foundCustomer;
		}
		throw new AuthenticationException("Incorrect username or password.");
	}
	
	//admin function
	public Customer getCustomerById(Long customerId) throws Exception {
		try {
			return customerRepository.findOne(customerId);
		} catch (Exception e) {
			logger.error("Exception occured while trying to retrieve customer: " + customerId, e);
			throw e;
		}
	}
	
	//admin function
	public Iterable<Customer> getCustomers() {
		return customerRepository.findAll();
	}
	
	//admin function
	public Customer updateCustomer(Customer customer, Long customerId) throws Exception {
		try {
			Customer oldCustomer = customerRepository.findOne(customerId);
			oldCustomer.setAddress(customer.getAddress());
			oldCustomer.setFirstName(customer.getFirstName());
			oldCustomer.setLastName(customer.getLastName());
			oldCustomer.setLevel(customer.getLevel());
			return customerRepository.save(oldCustomer);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to update customer: " + customerId, e);
			throw new Exception("Unable to update customer.");
		}
	}
	
	//admin function
	public void deleteCustomer(Long customerId) throws Exception {
		try {
			customerRepository.delete(customerId);
		} catch (Exception e) {
			logger.error("Exception occurred while trying to delete customer: " + customerId, e);
			throw new Exception("Unable to delete customer.");
		}
	}
	

}
