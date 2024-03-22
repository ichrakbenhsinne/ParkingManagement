package com.example.demo.Domain;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Blocks")
public class Block {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean IsOppen;
   

    public Boolean getIsOppen() {
        return IsOppen;
    }

    public void setIsOppen(Boolean isOppen) {
        IsOppen = isOppen;
    }





    public Long getBlockId() {
        return id;
    }
    
    
  


	public Block(Long blockId, Integer capacity, String blockName, BlockType blockType, List<Places> places,
    Parking parking) {
		super();
		this.id = blockId;
		this.capacity = capacity;
		this.blockName = blockName;
		this.blockType = blockType;
		this.places = places;
		this.parking = parking;
	}



	public Block() {
		
	}



	public void setBlockId(Long blockId) {
        this.id = blockId;
    }

    public Integer getCapacity() {
        return capacity != null ? capacity.intValue() : 0;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public String getBlockName() {
        return blockName;
    }
    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
    public BlockType getBlockType() {
        return blockType;
    }
    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
    public List<Places> getPlaces() {
        return places;
    }
    public void setPlaces(List<Places> places) {
        this.places = places;
    }
    public Parking getParking() {
        return parking;
    }
    public void setParking(Parking parking) {
        this.parking = parking;
    }
    private Integer capacity;
    private String blockName;
   
    @Enumerated(EnumType.STRING)
    private BlockType blockType;


    @OneToMany(mappedBy = "block")
    private List<Places> places;
    
    // @ManyToOne
     //@JoinColumn(name = "parking_id")
     //@JsonIgnore
     @ManyToOne
    private Parking parking;


    public String getIdentifiant() {
        return identifiant;
    }


    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    private String identifiant;
}






