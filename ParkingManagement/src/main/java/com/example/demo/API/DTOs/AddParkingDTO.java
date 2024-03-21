package com.example.demo.API.DTOs;

import com.example.demo.Domain.ParkingType;

public record AddParkingDTO(String id, String name, String description,Integer capacity, ParkingType parkingType, Integer BlockNumbers, double langitudeGeoPoint, double latitudeGeoPoint, String CityAddress, String CodeZoneAddress, String streetAddress, String LocationAddress) {


} 
