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
	// lấy tất cả danh sách hãng
	return brandDAO.findAll();
    }

    @Override
    public Brand getById(int id) {
	// TODO Auto-generated method stub
	return brandDAO.getById(id);
    }

    @Override
    public List<Integer> getTop4BrandByEvaluation() {
	// TODO Auto-generated method stub
	return brandDAO.getTop4BrandByEvaluation();
    }

}
