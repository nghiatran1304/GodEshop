package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.GlassMaterialDAO;
import com.godEShop.Entity.GlassMaterial;
import com.godEShop.Service.GlassMaterialService;

@Service
public class GlassMaterialServiceImpl implements GlassMaterialService{

    @Autowired
    GlassMaterialDAO gmDAO;
    
    @Override
    public List<GlassMaterial> findAll() {
	// TODO Auto-generated method stub
	return gmDAO.findAll();
    }

    @Override
    public GlassMaterial getById(Integer glassMaterialId) {
	// TODO Auto-generated method stub
	return gmDAO.getById(glassMaterialId);
    }

}
