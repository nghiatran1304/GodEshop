package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductPhoto;

@Repository
public interface ProductPhotoDAO extends JpaRepository<ProductPhoto, String> {

}
