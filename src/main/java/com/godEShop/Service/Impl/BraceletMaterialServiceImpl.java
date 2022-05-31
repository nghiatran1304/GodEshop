package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.BraceletMaterialDAO;
import com.godEShop.Entity.BraceletMaterial;
import com.godEShop.Service.BraceletMaterialService;

@Service
public class BraceletMaterialServiceImpl implements BraceletMaterialService{

    @Autowired
    BraceletMaterialDAO bmDAO;
    
    @Override
    public List<BraceletMaterial> findAll() {
	// TODO Auto-generated method stub
	return bmDAO.findAll();
    }

    @Override
    public BraceletMaterial getById(Integer id) {
	// TODO Auto-generated method stub
	return bmDAO.getById(id);
    }

}
