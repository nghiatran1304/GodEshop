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

	@Override
	public List<GlassMaterial> getAllGlassMaterialByName(String name) {
		// TODO Auto-generated method stub
		return gmDAO.getAllGlassMaterialByName(name);
	}

	

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		GlassMaterial gl = gmDAO.getById(id);
		gl.setAvailable(true);
		gmDAO.save(gl);
		
	}

	@Override
	public GlassMaterial update(GlassMaterial glassMaterial) {
		// TODO Auto-generated method stub
		return gmDAO.save(glassMaterial);
	}

	@Override
	public GlassMaterial create(GlassMaterial glassMaterial) {
		// TODO Auto-generated method stub
		return gmDAO.save(glassMaterial);
	}

	@Override
	public List<GlassMaterial> findAllGlassMaterial() {
		// TODO Auto-generated method stub
		return gmDAO.findAllGlassMaterial();
	}

}
