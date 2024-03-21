package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List; // Import correct pour la classe List

@Document(collection = "parkings")
public class Parking {
    
   @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    private String name;
    private String description;
    private Integer capacity;
    
    private ParkingType parkingType;

    
    //@OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
   // @JsonIgnore
    private List<String> blocksNames;
    
    @OneToOne(mappedBy = "parking")
    private GeoPoint coordinate;
    private Address parkingAddress;


    public String getParkingId() {
        return id;
    }


    public void setParkingId(String parkingId) {
        this.id = parkingId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getCapacity() {
        // Ensure that the list of blocks is not null
      //  if (blocks != null) {
            // Use stream() to iterate over the list and mapToInt() to extract capacities
            // Then, use sum() to get the total sum
        //    int capacity = 0;
        /*   for (Block block : blocks) {
                capacity += block.getCapacity();
            }
            return capacity;
        } else {
            return 0; // Return 0 if the list of blocks is null
        }*/  
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public ParkingType getParkingType() {
        return parkingType;
    }
    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }
    public List<String> getBlocksNames() {
        return blocksNames;
    }
    public void setBlocksNames(List<String> blocks) {
        this.blocksNames = blocks;
    }
    public GeoPoint getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(GeoPoint coordinate) {
        this.coordinate = coordinate;
    }
    public Address getParkingAddress() {
        return parkingAddress;
    }
    public void setParkingAddress(Address parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    
    public void calculateCapacity(List<Block> blocks) {
        int totalCapacity = 0;
        for (Block block : blocks) {
            totalCapacity += block.getCapacity();
        }
        this.capacity = totalCapacity;
    }


    public Parking(String parkingId, String name, String description, Integer capacity, ParkingType parkingType,
    List<String> blocksNames, GeoPoint coordinate, Address parkingAddress) {
this.id = parkingId;
this.name = name;
this.description = description;
this.capacity = 0;
this.parkingType = parkingType;
this.blocksNames = new ArrayList<>();
this.blocksNames.add("DefultBlock");
this.coordinate = coordinate;
this.parkingAddress = parkingAddress;
}


    public Parking() {
       
        this.capacity = 0;
        this.blocksNames = new ArrayList<>();
        this.blocksNames.add("defaultBlock");
    }

    


    // Constructeur, getters, setters, etc.



    
}
