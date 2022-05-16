package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.BraceletMaterial;

@Repository
public interface BraceletMaterialDAO extends JpaRepository<BraceletMaterial, String> {

}
