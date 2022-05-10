package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductLike;

@Repository
public interface ProductLikeDAO extends JpaRepository<ProductLike, Long> {

}
