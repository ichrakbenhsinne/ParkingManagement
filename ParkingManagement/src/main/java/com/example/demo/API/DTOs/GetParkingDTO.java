package com.example.demo.API.DTOs;

import com.example.demo.Domain.Address;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.GeoPoint;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingType;
import com.example.demo.Domain.Status;

import java.util.ArrayList;
import java.util.List;

public record GetParkingDTO(Long id, String name, String description, Integer capacity, ParkingType parkingType, List<String> blocksNames, Double langitudeGeoPoint, Double latitudeGeoPoint, String CityAddress, Integer CodeZoneAddress, String LocationAddress, String streetAddress, Status state) {

    public static GetParkingDTO mapFromParking(Parking parking) {
        Address parkingAddress = parking.getParkingAddress();
        GeoPoint coordinate = parking.getCoordinate();
        
        List<String> blocksNames = new ArrayList<>();
        for (Block block : parking.getBlocks()) {
            blocksNames.add(block.getBlockName());
        }
    
        Double longitude = null;
        Double latitude = null;
        
        if (coordinate != null) {
            longitude = coordinate.getLongitude();
            latitude = coordinate.getLatitude();
        }
    
        String cityAddress = null;
        Integer codeZoneAddress = null;
        String locationAddress = null;
        String streetAddress = null;
        
        if (parkingAddress != null) {
            cityAddress = parkingAddress.getCity();
            codeZoneAddress = parkingAddress.getCodeZone();
            locationAddress = parkingAddress.getLocation();
            streetAddress = parkingAddress.getStreet();
        }
    
        return new GetParkingDTO(
            parking.getParkingId(),
            parking.getName(),
            parking.getDescription(),
            parking.getCapacity(),
            parking.getParkingType(),
            blocksNames,
            longitude,
            latitude,
            cityAddress,
            codeZoneAddress,
            locationAddress,
            streetAddress,
            parking.getState()
        );
    }
     
}
