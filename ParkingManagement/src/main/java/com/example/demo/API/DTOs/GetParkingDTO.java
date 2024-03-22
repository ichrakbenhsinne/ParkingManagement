package com.example.demo.API.DTOs;

import com.example.demo.Domain.Address;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.GeoPoint;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingType;

import java.util.ArrayList;
import java.util.List;

public record GetParkingDTO(String name, String description, Integer capacity, ParkingType parkingType, List<Block> blocks, Double longitudeGeoPoint, Double latitudeGeoPoint, String cityAddress, Integer codeZoneAddress, String locationAddress, String streetAddress) {

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

        return new GetParkingDTO(
            parking.getName(),
            parking.getDescription(),
            parking.getCapacity(),
            parking.getParkingType(),
            parking.getBlocks(),
            longitude,
            latitude,
            parking.getParkingAddress().getCity(),
            parking.getParkingAddress().getCodeZone(),
            parking.getParkingAddress().getLocation(),
            parking.getParkingAddress().getStreet()
        );
    }
    
}
