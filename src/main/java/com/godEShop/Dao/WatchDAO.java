package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Watch;

@Repository
public interface WatchDAO extends JpaRepository<Watch, Integer>{

}
