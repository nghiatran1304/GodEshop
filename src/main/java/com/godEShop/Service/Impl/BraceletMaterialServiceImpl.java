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

	@Override
	public List<BraceletMaterial> getAllBraceletByName(String name) {
		// TODO Auto-generated method stub
		return bmDAO.getAllBraceletByName(name);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		BraceletMaterial br = bmDAO.getById(id);
		br.setAvailable(true);
		bmDAO.save(br);
	}

	@Override
	public BraceletMaterial update(BraceletMaterial braceletMaterial) {
		// TODO Auto-generated method stub
		return bmDAO.save(braceletMaterial);
	}

	@Override
	public BraceletMaterial create(BraceletMaterial braceletMaterial) {
		// TODO Auto-generated method stub
		return bmDAO.save(braceletMaterial);
	}

	@Override
	public List<BraceletMaterial> findAllBraceletMaterial() {
		// TODO Auto-generated method stub
		return bmDAO.findAllBraceletMaterial();
	}

}
