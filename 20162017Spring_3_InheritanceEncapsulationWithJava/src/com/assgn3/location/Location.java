package com.assgn3.location;

public class Location {
	private Double latitude;
	private Double longitude;
	
	
	
	public Location(Double latitude, Double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String toString(){
		return "Location: " + longitude.toString() + ", " + latitude.toString();
	}

}
