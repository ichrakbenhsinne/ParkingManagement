package com.example.demo.API.DTOs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.Domain.Block;
import com.example.demo.Domain.BlockType;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.Places;

public record GetBlockDTO(Long id, String blockName, String parkingName, Boolean isOpen, Integer capacity, BlockType blockType, List<String> placeNames) {

    public static GetBlockDTO mapFromBlock(Block block) {
        Parking parking = block.getParking();
        
        List<String> placeNames = new ArrayList<>();
        for (Places place : block.getPlaces()) {
            placeNames.add(place.getname());
        }

        return new GetBlockDTO(
            block.getBlockId(),
            block.getBlockName(),
            parking.getName(),
            block.getIsOppen(),
            block.getCapacity(),
            block.getBlockType(),
            placeNames
        );
    }
    
}