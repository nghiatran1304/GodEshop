package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.RefAccount;

@Repository
public interface RefAccountDAO extends JpaRepository<RefAccount, Long> {

}
