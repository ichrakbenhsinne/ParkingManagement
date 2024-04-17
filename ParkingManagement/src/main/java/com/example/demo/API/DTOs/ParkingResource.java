package com.example.demo.API.DTOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.ServiceParking;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.Places;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/parking")
public class ParkingResource {

    @Autowired
    ServiceParking serviceParking;
    
    

    @PostMapping("/addParking")
    public Parking addParking(@RequestBody AddParkingDTO addParkingDTO) {
    	System.out.println(addParkingDTO.state());
    	return serviceParking.addParking(
                addParkingDTO.name(),
                addParkingDTO.description(),
               
                addParkingDTO.parkingType(),
               
                addParkingDTO.langitudeGeoPoint(),
                addParkingDTO.latitudeGeoPoint(),
                addParkingDTO.CityAddress(),
                addParkingDTO.CodeZoneAddress(),
                addParkingDTO.streetAddress(),
                addParkingDTO.LocationAddress(),
                addParkingDTO.state()
                );
    }

    @GetMapping("/searchByName/{parkingName}")
    public ResponseEntity<GetParkingDTO> searchParkingByName(@PathVariable String parkingName) {
        Parking parking = serviceParking.getParkingByName(parkingName);
        
        if (parking != null) {
            GetParkingDTO parkingDTO = GetParkingDTO.mapFromParking(parking);
            return ResponseEntity.ok(parkingDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @GetMapping("/GetAllPark")
    public List<GetParkingDTO> getAllParkings( )
    {
        return serviceParking.getAllParkings();
    }

    
    // ajouter les conditions
    @DeleteMapping("/deleteByName/{parkname}")
    public void deleteParking(@PathVariable String parkname)

    {
       serviceParking.deleteParking(parkname);

    }

    @DeleteMapping("/deleteBlockByName/{blockname}")
    public void deleteBlock(@PathVariable String blockname)

    {
       serviceParking.deleteBlock(blockname);

    }

    @PostMapping("/addblock")
    public Block addBlock(@RequestBody AddBlockDTO AddBlockDTO) {
        // Ajoutez des logs pour déboguer
        System.out.println("Capacity: " + AddBlockDTO.capacity());
        System.out.println("BlockName: " + AddBlockDTO.blockName());
        System.out.println("BlockType: " + AddBlockDTO.blockType());

        return serviceParking.addBlock(
            AddBlockDTO.capacity(),
            AddBlockDTO.blockName(),
            AddBlockDTO.blockType()
        );
    }


    @GetMapping("/GetBlockByName/{Blockname}")
    public Block getBlockByName(@PathVariable String Blockname)

    {
        return serviceParking.getBlockByName(Blockname);
    }




    @GetMapping("/GetCapacityByBlock/{Blockname}")
    public Integer getCapacityByBlock(@PathVariable String Blockname)

    {
        return serviceParking.getBlockByName(Blockname).getCapacity();
    }

    @PostMapping("/AddBlockToParking/{ParkingName}")
    public Parking addBlockToParking(@PathVariable String ParkingName,@RequestBody Block block)
    {

       serviceParking.addBlockToParking(ParkingName, block);
       System.out.println(block.getBlockName());
       return serviceParking.getParkingByName(ParkingName);
    }

    @PostMapping("/AddPlaceToBlock/{BlockName}")
    public Block addPlaceToblock( @PathVariable String BlockName,@RequestBody Places place)
    
    {
        System.out.println(place.getname());
      serviceParking.addPlaceToBlock(BlockName, place);
      return serviceParking.getBlockByName(BlockName);

    }

    @GetMapping("/GetPlaceByName/{PlaceName}")
    public Places getPlaceByName(@PathVariable String PlaceName)

    {
        return serviceParking.getPlaceByName(PlaceName);
    }

  @GetMapping("/GetplacesByBlock/{blockName}")
public List<GetPlaceDTO> getplacesByBlock(@PathVariable String blockName) {
    List<Places> places = serviceParking.getPlacesByBlock(blockName);
    List<GetPlaceDTO> placeDTOs = new ArrayList<>();

    for (Places place : places) {
        placeDTOs.add(GetPlaceDTO.mapFromPlaces(place));
    }

    return placeDTOs;
}



@GetMapping("/GetBlocksByParking/{ParkingName}")
public List<GetBlockDTO> getBlocksByParking(@PathVariable String ParkingName) {
    List<Block> blocks = serviceParking.getBlocksByParking(ParkingName);
    List<GetBlockDTO> blockDTOs = new ArrayList<>();

    for (Block block : blocks) {
        blockDTOs.add(GetBlockDTO.mapFromBlock(block));
    }

    return blockDTOs;
}


     @DeleteMapping("/DeletePlaceByName/{placeName}/{blockname}")
     public void deletePlaceByName(@PathVariable String placeName, @PathVariable String blockname) {
         serviceParking.deletePlaceByName(placeName, blockname);
     }
     
   // @GetMapping("/GetStateByPlace/{PlaceName}")
    
    
   @GetMapping("/ModifiePlaceStatusToAvailable/{parkingName}/{BlockName}/{PlaceName}")
   public boolean updatePlaceStateToAvailable(@PathVariable String PlaceName, @PathVariable String BlockName, @PathVariable String parkingName) {
       return serviceParking.updatePlaceStateToAvailable(parkingName, BlockName, PlaceName);
   }
   
   @GetMapping("/ModifiePlaceStatusToNotAvailable/{parkingName}/{BlockName}/{PlaceName}")
   public boolean updatePlaceStateToNotAvailable(@PathVariable String PlaceName, @PathVariable String BlockName, @PathVariable String parkingName) {
       // Utilisez ! pour inverser le résultat booléen retourné par serviceParking.UpdatePlaceStateToAvailable
       return !serviceParking.updatePlaceStateToAvailable(parkingName, BlockName, PlaceName);
   }
   


   //  @GetMapping("/GetCapacityByParking/{ParkingName}")
   @GetMapping("/CloseBlock/{ParkingName}/{BlockName}")
public void closeBlock(@PathVariable String BlockName, @PathVariable String ParkingName) {
    serviceParking.fermerBlock(BlockName, ParkingName);
    System.out.println("Parking closed successfully");
}

@GetMapping("/OpenBlock/{ParkingName}/{BlockName}")
public void openBlock(@PathVariable String BlockName, @PathVariable String ParkingName) {
    serviceParking.ouvrirBlock(BlockName, ParkingName);
    System.out.println("Parking opened successfully");
}

@PutMapping("/UpdateParking/{id}")
public ResponseEntity<Object> updateParking(@PathVariable Long id, @RequestBody Parking newParking) {
    Parking updatedParking = serviceParking.updateParking(newParking, id);
    if (updatedParking != null) {
        GetParkingDTO parkingDTO = GetParkingDTO.mapFromParking(updatedParking);
        return ResponseEntity.ok(parkingDTO);
    } else {
        // Gérer le cas où updateParking renvoie null
        // Vous pouvez renvoyer une réponse avec un message d'erreur approprié
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking not found");
    }
}

@PutMapping("/UpdateBlock/{id}")
public ResponseEntity<Object> updateBlock(@PathVariable Long id, @RequestBody Block newBlock) {
    GetBlockDTO updatedBlock = serviceParking.updateBlock(id, newBlock);
    if (updatedBlock != null) {
        return ResponseEntity.ok(updatedBlock);
    } else {
        // Retournez une réponse avec un message d'erreur approprié
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Block not found");
    }

}
@PutMapping("/UpdatePlace/{id}")
public GetPlaceDTO updatePlace(@PathVariable Long id, @RequestBody Places newPlace) {
    System.out.println(newPlace.getname());
    return serviceParking.updatePlace(id, newPlace);
   
}

}





