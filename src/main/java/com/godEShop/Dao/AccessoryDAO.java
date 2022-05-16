package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Accessory;

@Repository
public interface AccessoryDAO extends JpaRepository<Accessory, Integer> {

}
