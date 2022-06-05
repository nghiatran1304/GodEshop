package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, String>{

}
