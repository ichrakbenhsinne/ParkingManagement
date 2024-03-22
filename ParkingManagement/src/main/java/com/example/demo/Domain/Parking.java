package com.example.demo.Domain;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List; // Import correct pour la classe List

@Entity
@Table(name = "parkings")
public class Parking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private Integer capacity;
    
    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;

    
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private List<Block> blocks;
    
    @OneToOne(mappedBy = "parking")
    private GeoPoint coordinate;
    @OneToOne(mappedBy = "parking")
    private Address parkingAddress;


    public Long getParkingId() {
        return id;
    }


    public void setParkingId(Long parkingId) {
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
        int totalCapacity = 0;
        if (blocks != null) {
            for (Block block : blocks) {
                totalCapacity += block.getCapacity();
            }
        }
        return totalCapacity;
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
    public List<Block> getBlocks() {
        return blocks;
    }
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
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


    public Parking(Long parkingId, String name, String description, Integer capacity, ParkingType parkingType,
    List<Block> blocksNames, GeoPoint coordinate, Address parkingAddress) {
this.id = parkingId;
this.name = name;
this.description = description;
this.capacity = 0;
this.parkingType = parkingType;
this.blocks = new ArrayList<>();
this.blocks.add(new Block());
this.coordinate = coordinate;
this.parkingAddress = parkingAddress;
}


    public Parking() {
       
        this.capacity = 0;
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block());
    }

    


    // Constructeur, getters, setters, etc.



    
}
