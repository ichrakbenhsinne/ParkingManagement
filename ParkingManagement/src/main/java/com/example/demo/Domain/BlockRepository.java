package com.example.demo.Domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    
    @Query("SELECT b FROM Block b WHERE b.blockName = ?1")
    Block findBlockByBlockName(@Param("blockName")String blockName);
    
}

