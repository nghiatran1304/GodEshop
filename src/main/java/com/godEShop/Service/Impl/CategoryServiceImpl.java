package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.CategoryDAO;
import com.godEShop.Entity.Category;
import com.godEShop.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryDAO categoryDao;
    
    @Override
    public List<Category> findAll() {
	// TODO Auto-generated method stub
	return categoryDao.findAll();
    }

}
