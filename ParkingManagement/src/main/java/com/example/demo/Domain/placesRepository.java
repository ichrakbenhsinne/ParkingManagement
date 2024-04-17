package com.example.demo.Domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface placesRepository extends JpaRepository<Places, Long> {
      @Query("SELECT p FROM Places p WHERE p.name = :name")
    Places findPlaceByName(@Param("name") String name);

    @Query("SELECT p FROM Places p WHERE p.id = :id")
    Places findPlaceByid(@Param("id") Long id);
}




