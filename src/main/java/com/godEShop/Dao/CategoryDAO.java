package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer>{

}
