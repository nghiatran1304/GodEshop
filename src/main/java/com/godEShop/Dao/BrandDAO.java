package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Brand;

@Repository
public interface BrandDAO extends JpaRepository<Brand, Long>{

}
