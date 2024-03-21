package com.example.demo.Domain;

import java.util.UUID;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.ManyToOne;

public class Places {

     @Id
     private String id;

    
   // private String placeName;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getBlockname() {
        return blockname;
    }
    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }
    private String Name;
    private boolean state;
  //  @ManyToOne
    private String blockname;

   
    
   
  
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public Places(String placeId, boolean state) {
        this.id = placeId;
        this.state = state;
    }
    public Places() {
    }
   
    
}
