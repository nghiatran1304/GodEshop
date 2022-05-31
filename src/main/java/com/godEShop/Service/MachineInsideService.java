package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.MachineInside;

public interface MachineInsideService {

    List<MachineInside> findAll();

    MachineInside getById(Integer machineInsideId);
    
}
