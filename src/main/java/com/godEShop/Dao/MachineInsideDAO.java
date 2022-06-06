package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Entity.MachineInside;

@Repository
public interface MachineInsideDAO extends JpaRepository<MachineInside, Integer> {

    @Query("SELECT mi FROM MachineInside mi WHERE mi.name LIKE ?1")
    List<MachineInside> getAllMachineInsideByName(String name);

    @Query("SELECT mi FROM MachineInside mi WHERE mi.available = 0")
    List<MachineInside> findAllMachineInside();

}
