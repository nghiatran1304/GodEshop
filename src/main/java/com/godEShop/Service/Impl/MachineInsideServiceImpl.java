package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.MachineInsideDAO;
import com.godEShop.Entity.MachineInside;
import com.godEShop.Service.MachineInsideService;

@Service
public class MachineInsideServiceImpl implements MachineInsideService{

    @Autowired
    MachineInsideDAO miDAO;
    
    
    @Override
    public List<MachineInside> findAll() {
	// TODO Auto-generated method stub
	return miDAO.findAll();
    }

    @Override
    public MachineInside getById(Integer machineInsideId) {
	// TODO Auto-generated method stub
	return miDAO.getById(machineInsideId);
    }

	@Override
	public List<MachineInside> getAllMachineInsideByName(String name) {
		// TODO Auto-generated method stub
		return miDAO.getAllMachineInsideByName(name);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		MachineInside mi = miDAO.getById(id);
		mi.setAvailable(true);
		miDAO.save(mi);
	}

	@Override
	public MachineInside update(MachineInside machineInside) {
		// TODO Auto-generated method stub
		return miDAO.save(machineInside);
	}

	@Override
	public MachineInside create(MachineInside machineInside) {
		// TODO Auto-generated method stub
		return  miDAO.save(machineInside);
	}

	@Override
	public List<MachineInside> findAllMachineInside() {
		// TODO Auto-generated method stub
		return miDAO.findAllMachineInside();
	}

}
