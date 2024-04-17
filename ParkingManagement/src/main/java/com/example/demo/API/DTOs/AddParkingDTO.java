package com.example.demo.API.DTOs;

import com.example.demo.Domain.ParkingType;
import com.example.demo.Domain.Status;

public record AddParkingDTO(String id, String name, String description,Integer capacity, ParkingType parkingType, Integer BlockNumbers, double langitudeGeoPoint, double latitudeGeoPoint, String CityAddress, Integer CodeZoneAddress, String streetAddress, String LocationAddress, Status state) {


} 
