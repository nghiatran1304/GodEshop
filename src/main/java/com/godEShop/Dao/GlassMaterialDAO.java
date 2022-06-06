package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.GlassMaterial;

@Repository
public interface GlassMaterialDAO extends JpaRepository<GlassMaterial, Integer> {

	@Query("Select g FROM GlassMaterial g WHERE g.name LIKE ?1")
	List<GlassMaterial> getAllGlassMaterialByName(String name);

	@Query("Select g FROM GlassMaterial g WHERE g.available = 0 ")
	List<GlassMaterial> findAllGlassMaterial();
}
