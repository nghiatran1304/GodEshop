package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.BrandDAO;
import com.godEShop.Entity.Brand;
import com.godEShop.Service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{
    
    @Autowired
    BrandDAO brandDAO;

    @Override
    public List<Brand> findAll() {
	// TODO Auto-generated method stub
	return brandDAO.findAll();
    }

}
