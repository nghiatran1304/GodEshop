package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.OrderStatus;

@Repository
public interface OrderStatusDAO extends JpaRepository<OrderStatus, Integer> {
    
}
