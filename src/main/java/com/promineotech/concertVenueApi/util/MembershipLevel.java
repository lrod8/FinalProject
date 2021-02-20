package com.promineotech.concertVenueApi.util;

public enum MembershipLevel {

	BALCONY(50.00),
	FLOOR(100.00),
	ORCHESTRA(200.00);
	
	private double seatPrice;
	
	MembershipLevel(double seat) {
		this.seatPrice = seat;
	}
	
	public double getSeat() {
		return seatPrice;
	}
	
}
