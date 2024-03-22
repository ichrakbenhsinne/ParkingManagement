package com.example.demo.API.DTOs;

import com.example.demo.Domain.Places;

public record GetPlaceDTO(String placeName, String blockName, boolean state) {

    public static GetPlaceDTO mapFromPlaces(Places place) {
        return new GetPlaceDTO(
            place.getName(),
            place.getBlock().getBlockName(),
            place.isState()
        );
    }
    
}