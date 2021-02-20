package com.promineotech.concertVenueApi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promineotech.concertVenueApi.entity.Concert;
import com.promineotech.concertVenueApi.repository.ConcertRepository;

@Service
public class ConcertService {
	
	private static final Logger logger = LogManager.getLogger(ConcertService.class);
	
	@Autowired
	private ConcertRepository concertRepository;
	
	public Iterable<Concert> getConcerts() {
		return concertRepository.findAll();
	}
	
	public Concert createConcert(Concert concert) {
		return concertRepository.save(concert);
	}
	
	public Concert updateConcert(Concert concert, Long concertId) throws Exception {
		try {
			Concert oldConcert = concertRepository.findOne(concertId);
			oldConcert.setName(concert.getName());
			oldConcert.setDescription(concert.getDescription());
			oldConcert.setPrice(concert.getPrice());
			return concertRepository.save(oldConcert);
		} catch (Exception e) {
			logger.error("Exception occured while trying to update concert id: " + concertId, e);
			throw new Exception("Unable to update concert.");
		}
	}
	
	public void removeConcert(Long concertId) throws Exception {
		try {
			concertRepository.delete(concertId);
		} catch(Exception e) {
			logger.error("Exception occured while trying to delete concert id: " + concertId, e);
			throw new Exception("Unable to delete concert.");
		}
	}

}
