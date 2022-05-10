package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, String> {

}
