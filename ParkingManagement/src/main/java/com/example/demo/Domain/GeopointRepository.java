package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeopointRepository extends MongoRepository<GeoPoint, String>{
    


}
