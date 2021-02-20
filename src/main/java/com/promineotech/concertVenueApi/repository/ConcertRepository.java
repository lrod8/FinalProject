package com.promineotech.concertVenueApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.promineotech.concertVenueApi.entity.Concert;

public interface ConcertRepository extends CrudRepository<Concert, Long>{

}
