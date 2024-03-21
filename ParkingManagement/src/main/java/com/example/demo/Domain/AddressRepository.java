package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {




}