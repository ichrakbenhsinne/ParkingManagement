package com.example.demo.API.DTOs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Application.ServiceParking;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.Places;

@RestController
@RequestMapping("/parking")
public class ParkingResource {

    @Autowired
    ServiceParking serviceParking;
    
    

    @PostMapping("/addParking")
    public Parking addParking(@RequestBody AddParkingDTO addParkingDTO) {
    	
    	return serviceParking.addParking(
                addParkingDTO.name(),
                addParkingDTO.description(),
               
                addParkingDTO.parkingType(),
               
                addParkingDTO.langitudeGeoPoint(),
                addParkingDTO.latitudeGeoPoint(),
                addParkingDTO.CityAddress(),
                addParkingDTO.CodeZoneAddress(),
                addParkingDTO.streetAddress(),
                addParkingDTO.LocationAddress());
    }

    @GetMapping("/searchByName/{parkingName}")
    public ResponseEntity<Parking> searchParkingByName(@PathVariable String parkingName) {
        Parking parking = serviceParking.GetParkingByName(parkingName);

        if (parking != null) {
            return ResponseEntity.ok(parking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/GetAllPark")
    public List<GetParkingDTO> GetAllParkings( )
    {
        return serviceParking.GetAllParkings();
    }

    
    // ajouter les conditions
    @DeleteMapping("/deleteByName/{parkname}")
    public void deleteParking(@PathVariable String parkname)

    {
       serviceParking.DeleteParking(parkname);

    }

    @PostMapping("/addblock")
    public Block AddBlock(@RequestBody AddBlockDTO AddBlockDTO) {
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
    public Block GetBlockByName(@PathVariable String Blockname)

    {
        return serviceParking.GetBlockByName(Blockname);
    }

    @GetMapping("/GetCapacityByBlock/{Blockname}")
    public Integer GetCapacityByBlock(@PathVariable String Blockname)

    {
        return serviceParking.GetBlockByName(Blockname).getCapacity();
    }

    @GetMapping("/AddBlockToParking/{ParkingName}")
    public Parking AddBlockToParking(@PathVariable String ParkingName,@RequestBody Block block)

    {

       serviceParking.AddBlockToParking(ParkingName, block);
       System.out.println(block.getBlockName());
       return serviceParking.GetParkingByName(ParkingName);
    }

    @GetMapping("/AddPlaceToBlock/{ParkingName}/{BlockName}")
    public Block AddPlaceToParking(@PathVariable String ParkingName, @PathVariable String BlockName,@RequestBody Places place)
    
    {
        System.out.println(place.getName());
      serviceParking.AddPlaceToBlock(BlockName, ParkingName, place);
      return serviceParking.GetBlockByName(BlockName);

    }

    @GetMapping("/GetPlaceByName/{PlaceName}")
    public Places GetPlaceByName(@PathVariable String PlaceName)

    {
        return serviceParking.GetPlaceByName(PlaceName);
    }

  
     @GetMapping("/GetplacesByBlock/{blockName}")
     public List <Places> getplacesByBlock(@PathVariable String blockName)
     {

        return serviceParking.GetplacesByBlock(blockName);
     }


     @GetMapping("/GetBlocksByParking/{ParkingName}")
     public List <Block> getblocksByParking(@PathVariable String ParkingName)
     {
        return serviceParking.GetBlocksByParking(ParkingName);
     }

     @DeleteMapping("/DeletePlaceByName/{placeName}/{blockname}")
     public void DeletePlaceByName(@PathVariable String placeName, @PathVariable String blockname) {
         serviceParking.deletePlaceByName(placeName, blockname);
     }
     
   // @GetMapping("/GetStateByPlace/{PlaceName}")
    
    
   @GetMapping("/ModifiePlaceStatusToAvailable/{parkingName}/{BlockName}/{PlaceName}")
   public boolean UpdatePlaceStateToAvailable(@PathVariable String PlaceName, @PathVariable String BlockName, @PathVariable String parkingName) {
       return serviceParking.UpdatePlaceStateToAvailable(parkingName, BlockName, PlaceName);
   }
   
   @GetMapping("/ModifiePlaceStatusToNotAvailable/{parkingName}/{BlockName}/{PlaceName}")
   public boolean UpdatePlaceStateToNotAvailable(@PathVariable String PlaceName, @PathVariable String BlockName, @PathVariable String parkingName) {
       // Utilisez ! pour inverser le résultat booléen retourné par serviceParking.UpdatePlaceStateToAvailable
       return !serviceParking.UpdatePlaceStateToAvailable(parkingName, BlockName, PlaceName);
   }
   


   //  @GetMapping("/GetCapacityByParking/{ParkingName}")
   @GetMapping("/CloseBlock/{ParkingName}/{BlockName}")
public void CloseBlock(@PathVariable String BlockName, @PathVariable String ParkingName) {
    serviceParking.FermerBlock(BlockName, ParkingName);
    System.out.println("Parking closed successfully");
}

@GetMapping("/OpenBlock/{ParkingName}/{BlockName}")
public void OpenBlock(@PathVariable String BlockName, @PathVariable String ParkingName) {
    serviceParking.OuvrirBlock(BlockName, ParkingName);
    System.out.println("Parking opened successfully");
}

}





