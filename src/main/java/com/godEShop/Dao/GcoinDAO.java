package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Gcoin;

@Repository
public interface GcoinDAO extends JpaRepository<Gcoin, Integer> {

}
