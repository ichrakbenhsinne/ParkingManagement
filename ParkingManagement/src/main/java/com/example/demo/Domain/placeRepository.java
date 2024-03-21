package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface placeRepository extends MongoRepository<Places, String> {
    
   @Query("{Name: '?0'}")
   Places findPlacebyidentf(String Name);
	

}
