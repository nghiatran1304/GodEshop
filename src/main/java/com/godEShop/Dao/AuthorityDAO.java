package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Authority;

@Repository
public interface AuthorityDAO extends JpaRepository<Authority, Integer> {

}
