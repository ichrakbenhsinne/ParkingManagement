package com.example.demo.Domain;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Document(collection = "Addresses")

public class Address {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

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


	public String getId() {
        return id;
    }
    public void setId(String id) {
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
}
