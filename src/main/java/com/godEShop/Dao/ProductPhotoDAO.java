package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductPhoto;

@Repository
public interface ProductPhotoDAO extends JpaRepository<ProductPhoto, String> {
    
    @Query("SELECT MIN(pp.id) FROM ProductPhoto pp WHERE pp.product.id = ?1")
    String productFirstPhotoname(Long productId);
    
}
