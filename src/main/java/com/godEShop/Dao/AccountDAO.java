package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, String> {
    @Query("SELECT a FROM Account a WHERE a.username=?1 and a.isDelete=false")
    Account findByUsername(String username);

}
