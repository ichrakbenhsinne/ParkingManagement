package com.example.demo.API.DTOs;

import com.example.demo.Domain.Places;

public record GetPlaceDTO(Long id, String name, String blockName, boolean state) {

    public static GetPlaceDTO mapFromPlaces(Places place) {
        return new GetPlaceDTO(
            place.getId(),
            place.getname(),
            place.getBlock().getBlockName(),
            place.isState()
        );
    }
}