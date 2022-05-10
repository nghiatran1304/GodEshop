package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.History;

@Repository
public interface HistoryDAO extends JpaRepository<History, Long> {

}
