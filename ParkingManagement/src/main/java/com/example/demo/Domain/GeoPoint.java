package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Document(collection = "geoPoints")
public class GeoPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idGeolocation;
    private double longitude; // Corrected from langitude to longitude
    private double latitude;

    
    
    
    
     public GeoPoint() {
    	 
	}

	public String getIdGeolocation() {
        return idGeolocation;
    }

    public void setIdGeolocation(String idGeolocation) {
        this.idGeolocation = idGeolocation;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @OneToOne(mappedBy = "coordinate")
    private Parking parking;

}
