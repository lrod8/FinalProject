package com.promineotech.concertVenueApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.concertVenueApi.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
