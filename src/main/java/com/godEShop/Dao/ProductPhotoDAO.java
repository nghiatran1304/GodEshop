package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.godEShop.Entity.ProductPhoto;

@Repository
public interface ProductPhotoDAO extends JpaRepository<ProductPhoto, String> {
    
    @Query("SELECT MIN(pp.id) FROM ProductPhoto pp WHERE pp.product.id = ?1")
    String productFirstPhotoname(Long productId);
    
    @Query("SELECT pp FROM ProductPhoto pp WHERE pp.product.id = ?1")
    List<ProductPhoto> getAllProductPhotoByProductId(Long id);
    
    @Query("SELECT pp.id FROM ProductPhoto pp WHERE pp.product.id = ?1")
    List<String> getAllNamePhotoByProductId(Long id);
    
    @Transactional
    @Modifying
    @Query("DELETE ProductPhoto pp WHERE pp.id = ?1")
    void deletePhoto(String id);
}
