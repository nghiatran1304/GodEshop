package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long>{

}
