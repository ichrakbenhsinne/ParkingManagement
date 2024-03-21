package com.example.demo.API.DTOs;

import com.example.demo.Domain.Address;
import com.example.demo.Domain.Block;
import com.example.demo.Domain.GeoPoint;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.ParkingType;

import java.util.List;

public record GetParkingDTO(String name, String description, Integer capacity, ParkingType parkingType, List<String> blocksnames, double longitudeGeoPoint, double latitudeGeoPoint, String cityAddress, Integer codeZoneAddress, String locationAddress, String streetAddress) {

    public static GetParkingDTO mapFromParking(Parking parking) {
        Address parkingAddress = parking.getParkingAddress();
        GeoPoint coordinate = parking.getCoordinate();
    
        return new GetParkingDTO(
            parking.getName(),
            parking.getDescription(),
            parking.getCapacity(),
            parking.getParkingType(),
            parking.getBlocksNames(),
            parking.getCoordinate().getLongitude(),
            parking.getCoordinate().getLatitude(),
            parking.getParkingAddress().getCity(),
            parking.getParkingAddress().getCodeZone(),
            parking.getParkingAddress().getLocation(),
            parking.getParkingAddress().getStreet()
        );
    }
    
}
