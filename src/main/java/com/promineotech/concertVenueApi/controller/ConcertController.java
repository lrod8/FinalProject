package com.promineotech.concertVenueApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.concertVenueApi.entity.Concert;
import com.promineotech.concertVenueApi.service.ConcertService;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

	@Autowired
	private ConcertService concertService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> getConcerts() {
		return new ResponseEntity<Object>(concertService.getConcerts(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> createConcert(@RequestBody Concert concert) {
		return new ResponseEntity<Object>(concertService.createConcert(concert), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{concertId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateConcert(@RequestBody Concert concert, @PathVariable Long concertId) {
		try {
			return new ResponseEntity<Object>(concertService.updateConcert(concert, concertId), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>("Unable to update concert.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/{concertId}",  method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteConcert(@PathVariable Long concertId) {
		try {
			concertService.removeConcert(concertId);
			return new ResponseEntity<Object>("Successfully deleted concert with id: " + concertId, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Object>("Unable to delete concert.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
