package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.ProductDiscount;

@Repository
public interface ProductDiscountDAO extends JpaRepository<ProductDiscount, Long> {

}
