package com.example.demo.Domain;
import java.util.UUID;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
@Table(name = "Places")

public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
   // private String placeName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public Block getBlock() {
        return block;
    }
    public void setBlock(Block block) {
        this.block = block;
    }
    private String Name;
    private boolean state;

   @ManyToOne
   private Block block;
   // @JoinColumn(name = "block_id")
   // @JsonIgnore
    //private Block block;

   
    
   
  
    public boolean isState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public Places(Long placeId, boolean state) {
        this.id = placeId;
        this.state = state;
    }
    public Places() {
    }
   
    
}
