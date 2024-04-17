package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire

    @Query("SELECT p FROM Parking p WHERE p.name = :name")
    Parking findParkingbyidentf(@Param("name") String name);

    @Query("SELECT p FROM Parking p WHERE p.id = :id")
	Parking findparkingById(@Param("id") Long id);
}
