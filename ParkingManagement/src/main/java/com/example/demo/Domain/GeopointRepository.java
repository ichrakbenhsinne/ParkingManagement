package com.example.demo.Domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeopointRepository extends JpaRepository<GeoPoint, Long>{
    


}
