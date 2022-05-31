package com.godEShop.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.MachineInside;

@Repository
public interface MachineInsideDAO extends JpaRepository<MachineInside, Integer>{

}
