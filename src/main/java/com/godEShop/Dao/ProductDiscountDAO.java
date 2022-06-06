package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductDiscount;

@Repository
public interface ProductDiscountDAO extends JpaRepository<ProductDiscount, Long> {

    @Query("SELECT pd FROM ProductDiscount pd WHERE pd.product.id=?1")
    ProductDiscount getProductDiscount(Long productId);

}
