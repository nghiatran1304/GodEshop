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

}
