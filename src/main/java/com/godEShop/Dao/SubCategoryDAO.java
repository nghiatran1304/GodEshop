package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.SubCategory;

@Repository
public interface SubCategoryDAO extends JpaRepository<SubCategory, Integer> {

}
