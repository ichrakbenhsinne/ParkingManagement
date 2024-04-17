package com.example.demo.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Addresses")

public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    
    public Address(String city, String street, Integer codeZone, String location) {
        this.city = city;
        this.street = street;
        this.codeZone = codeZone;
        this.location = location;
    }
    
    
    public Address() {
    	
		
	}


	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public Integer getCodeZone() {
        return codeZone;
    }
    public void setCodeZone(Integer codeZone) {
        this.codeZone = codeZone;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    private Integer codeZone ;
    private String location;

    @OneToOne
    @JoinColumn(name = "parking_id")
    private Parking parking ;

    public Parking getParking() {
        return parking;
    }


    public void setParking(Parking parking) {
        this.parking = parking;
    }
}
