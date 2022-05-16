package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.GlassMaterial;

@Repository
public interface GlassMaterialDAO extends JpaRepository<GlassMaterial, Integer>{

}
