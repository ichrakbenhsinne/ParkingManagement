package com.example.demo.Domain;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends MongoRepository<Block, String> {
    
    @Query("{blockName : ?0}")
    Block findBlockByBlockName(String blockName);
    
}
