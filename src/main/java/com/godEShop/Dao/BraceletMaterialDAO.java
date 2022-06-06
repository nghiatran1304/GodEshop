package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.BraceletMaterial;

@Repository
public interface BraceletMaterialDAO extends JpaRepository<BraceletMaterial, Integer> {

    @Query("SELECT br FROM BraceletMaterial br WHERE br.name LIKE ?1")
    List<BraceletMaterial> getAllBraceletByName(String name);

    @Query("SELECT br FROM BraceletMaterial br WHERE br.available =0")
    List<BraceletMaterial> findAllBraceletMaterial();

}
