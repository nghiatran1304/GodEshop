package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

}
