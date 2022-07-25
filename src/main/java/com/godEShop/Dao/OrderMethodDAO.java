package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.OrderMethod;

@Repository
public interface OrderMethodDAO extends JpaRepository<OrderMethod, Integer> {

}
