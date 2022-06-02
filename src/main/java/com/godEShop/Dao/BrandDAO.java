package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Brand;

@Repository
public interface BrandDAO extends JpaRepository<Brand, Integer>{
    
    @Query(value = "{CALL sp_getTop4BrandByEvaluation()}", nativeQuery = true)
    List<Integer> getTop4BrandByEvaluation();
    
    @Query("SELECT b FROM Brand b WHERE b.name LIKE ?1")
    List<Brand> getAllBrandByName(String name);
    
}
