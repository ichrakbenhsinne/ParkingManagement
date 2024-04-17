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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a parking facility.
 */
@Entity
@Table(name = "parkings")
public class Parking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;// Parking name
    private String description; // Parking description
    private Integer capacity;  // Total parking capacity
    @Enumerated(EnumType.STRING)
    private ParkingType parkingType;  // Parking type

    
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Block> blocks;
    
    @OneToOne(mappedBy = "parking")
    @JsonIgnore
    private GeoPoint coordinate; // Parking coordinates

    @OneToOne(mappedBy = "parking")
    @JsonIgnore
    private Address parkingAddress;  // Parking address
    
    private Status state;  // Parking state


     /**
     * Gets the state of the parking.
     * @return The parking state.
     */
    public Status getState() {
        return state;
    }


    /**
     * Sets the state of the parking.
     * @param The parking state.
     */
    public void setState( Status state) {
        this.state = state;
    }

    /**
     * gets the id of the parking.
     * @return The parking id.
     */
    public Long getParkingId() {
        return id;
    }

/**
     * Sets the id of the parking.
     * @param The parking id.
     */
    public void setParkingId(Long parkingId) {
        this.id = parkingId;
    }


    /**
     * Gets the name of the parking.
     * @return The name of the parking.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the parking.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the parking.
     * @return The description of the parking.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of the parking.
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Calculates the total capacity of the parking.
     * @return The total capacity of the parking.
     */
    public Integer getCapacity() {
        int totalCapacity = 0;
        if (blocks != null) {
            for (Block block : blocks) {
                totalCapacity += block.getCapacity();
            }
        }
        return totalCapacity;
    }
    
    /**
     * Sets the capacity of the parking.
     * @param capacity The capacity to set.
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the type of the parking.
     * @return The type of the parking.
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * Sets the type of the parking.
     * @param parkingType The type to set.
     */
    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    /**
     * Gets the list of blocks in the parking.
     * @return The list of blocks.
     */
    public List<Block> getBlocks() {
        return blocks;
    }
    
    /**
     * Sets the list of blocks in the parking.
     * @param blocks The list of blocks to set.
     */
    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * Gets the coordinate of the parking.
     * @return The coordinate of the parking.
     */
    public GeoPoint getCoordinate() {
        return coordinate;
    }
    
    /**
     * Sets the coordinate of the parking.
     * @param coordinate The coordinate to set.
     */
    public void setCoordinate(GeoPoint coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Gets the address of the parking.
     * @return The address of the parking.
     */
    public Address getParkingAddress() {
        return parkingAddress;
    }
    
    /**
     * Sets the address of the parking.
     * @param parkingAddress The address to set.
     */
    public void setParkingAddress(Address parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    /**
     * Calculates the total capacity of the parking based on the provided list of blocks.
     * @param blocks The list of blocks to calculate the capacity from.
     */
    public void calculateCapacity(List<Block> blocks) {
        int totalCapacity = 0;
        for (Block block : blocks) {
            totalCapacity += block.getCapacity();
        }
        this.capacity = totalCapacity;
    }

        /**
     * Constructs a new Parking object with specified parameters.
     * @param parkingId The ID of the parking.
     * @param name The name of the parking.
     * @param description The description of the parking.
     * @param capacity The capacity of the parking.
     * @param parkingType The type of the parking.
     * @param blocksNames The list of blocks in the parking.
     * @param coordinate The coordinate of the parking.
     * @param parkingAddress The address of the parking.
     */
    public Parking(Long parkingId, String name, String description, Integer capacity, ParkingType parkingType,
                   List<Block> blocksNames, GeoPoint coordinate, Address parkingAddress) {
        this.id = parkingId;
        this.name = name;
        this.description = description;
        this.capacity = 0; // Initially set capacity to 0, will be calculated later
        this.parkingType = parkingType;
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block()); // Add a default block
        this.coordinate = coordinate;
        this.parkingAddress = parkingAddress;
    }

    /**
     * Constructs a new Parking object with default values.
     * This constructor is used for creating an empty Parking object.
     */
    public Parking() {
        this.capacity = 0;
        this.blocks = new ArrayList<>();
        this.blocks.add(new Block()); // Add a default block
    }

    // Additional constructors, getters, setters, etc.

}
