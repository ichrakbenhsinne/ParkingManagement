package com.example.demo.Domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Document(collection = "blocks")
public class Block {
     
    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Boolean IsOppen;
   
    public Boolean getIsOppen() {
        return IsOppen;
    }





    public void setIsOppen(Boolean isOppen) {
        IsOppen = isOppen;
    }





    public String getBlockId() {
        return id;
    }
    
    
  


	public Block(String blockId, Integer capacity, String blockName, BlockType blockType, List<String> places,
    String parkingName) {
		super();
		this.id = blockId;
		this.capacity = capacity;
		this.blockName = blockName;
		this.blockType = blockType;
		this.placesnames = places;
		this.parkingName = parkingName;
	}



	public Block() {
		
	}



	public void setBlockId(String blockId) {
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
    public List<String> getPlaces() {
        return placesnames;
    }
    public void setPlaces(List<String> places) {
        this.placesnames = places;
    }
    public String getParkingName() {
        return parkingName;
    }
    public void setParkingname(String parkingName) {
        this.parkingName = parkingName;
    }
    private Integer capacity;
    private String blockName;
   

    private BlockType blockType;
    @OneToMany(mappedBy = "block")
    private List<String> placesnames;
    
    //@ManyToOne
    //@JoinColumn(name = "parkingId")
    //@JsonIgnore

    private String parkingName;
    public String getIdentifiant() {
        return identifiant;
    }


    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    private String identifiant;
}






