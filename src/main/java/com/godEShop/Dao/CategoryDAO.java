package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.name LIKE ?1")
    List<Category> getAllBrandByName(String name);

    @Query("SELECT c FROM Category c WHERE c.available = 0")
    List<Category> findAllCategory();
    
}
