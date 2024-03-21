package com.example.demo.Domain;

import java.util.UUID;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

@Repository
public interface ParkingRepository extends MongoRepository<Parking, String> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire


   
	@Query("{name:'?0'}")
	Parking findParkingbyidentf(String name);
	



}