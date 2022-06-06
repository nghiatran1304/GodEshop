package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.MachineInside;

public interface MachineInsideService {

	List<MachineInside> getAllMachineInsideByName(String name);
	
    List<MachineInside> findAll();

    MachineInside getById(Integer machineInsideId);
    
    
    void delete(Integer id);

    MachineInside update(MachineInside machineInside);

    MachineInside create(MachineInside machineInside);

    List<MachineInside> findAllMachineInside();
    
}
