package com.example.demo.API.DTOs;

import java.util.List;

import com.example.demo.Domain.BlockType;
import com.example.demo.Domain.Parking;
import com.example.demo.Domain.Places;

public record AddBlockDTO(String blockId, Integer capacity, String blockName, BlockType blockType) {

} 