package com.example.demo.Domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "geoPoints")

public class GeoPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGeolocation;
    private double longitude; // Corrected from langitude to longitude
    private double latitude;

    
    
    
    
     public GeoPoint() {
    	 
	}

	public Long getIdGeolocation() {
        return idGeolocation;
    }

    public void setIdGeolocation(Long idGeolocation) {
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

    @OneToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

}
