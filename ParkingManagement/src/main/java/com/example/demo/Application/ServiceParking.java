package com.example.demo.Application;

import java.util.UUID;



import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.API.DTOs.AddParkingDTO;
import com.example.demo.API.DTOs.GetBlockDTO;
import com.example.demo.API.DTOs.GetParkingDTO;
import com.example.demo.API.DTOs.GetPlaceDTO;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.BlockType;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingType;
import com.example.demo.Domain.Places;
import com.example.demo.Domain.Status;

@Service
public interface ServiceParking {
    
 Parking addParking(String name, String description, ParkingType parkingType, double langitudeGeoPoint, double latitudeGeoPoint, String CityAddress, Integer CodeZoneAddress, String streetAddress, String LocationAddress, Status state); 
 Parking updateParking(Parking NewParking, Long id );
 void deleteParking(String ParkingName); // avec des conditions
 Parking getParkingByName( String ParkingName);
 List <GetParkingDTO> getAllParkings();
 List<Block> getBlocksByParking(String ParkingName);
 Block addBlock( Integer capacity, String blockName, BlockType blockType );
 public Block getBlockByName(String BlockName)   ;     
 public void addBlockToParking(String ParkingName, Block block);
 GetBlockDTO updateBlock(Long id, Block newBlock); // capacity, type, 
 void fermerBlock(String BlockName, String ParkingName);
 void ouvrirBlock(String BlockName, String ParkingName);
 Boolean updatePlaceStateToAvailable(String PlaceName, String Blockname, String ParkingName);
 void deleteBlock(String Blockname);
 Places getPlaceByName(String PlaceName);  
 List<Places> getPlacesByBlock(String BlockName);
 void addPlaceToBlock(String BlockName, Places place);
 void fermerParking( String ParkingName);
 void ouvrirParking(String ParkingName);
 Void publierParking(Parking parking);  // publier parking dans le map 
 void deletePlaceByName(String pLaceName, String BlockName);
 GetPlaceDTO updatePlace(Long id, Places newplace);

}
