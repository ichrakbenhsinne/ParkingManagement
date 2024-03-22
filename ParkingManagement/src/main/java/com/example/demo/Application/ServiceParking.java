package com.example.demo.Application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.API.DTOs.GetParkingDTO;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.BlockType;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingType;
import com.example.demo.Domain.Places;

@Service
public interface ServiceParking {
    
 Parking addParking(String name, String description, ParkingType parkingType, double langitudeGeoPoint, double latitudeGeoPoint, String CityAddress, String CodeZoneAddress, String streetAddress, String LocationAddress); 
 Parking UpdateParking(Parking NewParking, String ParkingName );
 void DeleteParking(String ParkingName); // avec des conditions
 Parking GetParkingByName( String ParkingName);
 List <GetParkingDTO> GetAllParkings();
 List<Block> GetBlocksByParking(String ParkingName);
 Block addBlock( Integer capacity, String blockName, BlockType blockType );
 public Block GetBlockByName(String BlockName)   ;     
 public void AddBlockToParking(String ParkingName, Block block);
 Void UpdateBlock(String BlockName, String ParkingName); // capacity, type, 
 void FermerBlock(String BlockName, String ParkingName);
 void OuvrirBlock(String BlockName, String ParkingName);


 Boolean UpdatePlaceStateToAvailable(String PlaceName, String Blockname, String ParkingName);


 Places GetPlaceByName(String PlaceName);  
 List<Places> GetplacesByBlock(String BlockName);
 void AddPlaceToBlock(String BlockName, String ParkingName, Places place);
 void FermerParking( String ParkingName);
 void OuvrirParking(String ParkingName);
 Void PublierParking(Parking parking);  // publier parking dans le map 
void deletePlaceByName(String pLaceName, String BlockName);
 

}
