package com.promineotech.concertVenueApi.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.concertVenueApi.entity.Concert;
import com.promineotech.concertVenueApi.entity.Customer;
import com.promineotech.concertVenueApi.entity.Order;
import com.promineotech.concertVenueApi.repository.ConcertRepository;
import com.promineotech.concertVenueApi.repository.CustomerRepository;
import com.promineotech.concertVenueApi.repository.OrderRepository;
import com.promineotech.concertVenueApi.util.MembershipLevel;

@Service
public class OrderService {
	
	private static final Logger logger = LogManager.getLogger(OrderService.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ConcertRepository concertRepository;
	
	public Order submitNewOrder(Set<Long> concertIds, Long customerId) throws Exception {
		try {
			Customer customer = customerRepository.findOne(customerId);
			Order order = initializeNewOrder(concertIds, customer);
			return orderRepository.save(order);
		} catch(Exception e) {
			logger.error("Exception occured while trying to create new order for customer: " + customerId, e);
			throw e;
		}
	}
	
	private Order initializeNewOrder(Set<Long> concertIds, Customer customer) {
		Order order =  new Order();
		order.setConcerts(convertToConcertSet(concertRepository.findAll(concertIds)));
		order.setDateOrdered(LocalDate.now());
		order.setCustomer(customer);
		order.setInvoiceAmount(calculateOrderTotal(order.getConcerts(), customer.getLevel()));
		addOrderToConcerts(order);
		return order;
	}
	
	private Set<Concert> convertToConcertSet(Iterable<Concert> iterable) {
		Set<Concert> set = new HashSet<Concert>();
		for (Concert concert : iterable) {
			set.add(concert);
		}
		return set;
	}
	
	private double calculateOrderTotal(Set<Concert> concerts, MembershipLevel level) {
		double total = 0;
		for (Concert concert : concerts) {
			total += concert.getPrice();
		}
		return total + level.getSeat();
	}
	
	private void addOrderToConcerts(Order order) {
		Set<Concert> concerts = order.getConcerts();
		for (Concert concert : concerts) {
			concert.getOrders().add(order);
		}
	}

}
