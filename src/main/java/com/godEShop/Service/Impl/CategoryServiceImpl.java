package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.CategoryDAO;
import com.godEShop.Entity.Category;
import com.godEShop.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDao;

    @Override
    public List<Category> findAll() {
	// TODO Auto-generated method stub
	return categoryDao.findAll();
    }

    @Override
    public Category getById(int id) {
	// TODO Auto-generated method stub
	return categoryDao.getById(id);
    }

    @Override
    public List<Category> getAllCateoryByName(String string) {
	// TODO Auto-generated method stub
	return categoryDao.getAllBrandByName(string);
    }

    @Override
    public void delete(Integer id) {
	// TODO Auto-generated method stub
	Category c = categoryDao.getById(id);
	c.setAvailable(true);
	categoryDao.save(c);
    }

    @Override
    public Category update(Category category) {
	// TODO Auto-generated method stub
	return categoryDao.save(category);
    }

    @Override
    public Category create(Category category) {
	// TODO Auto-generated method stub
	return categoryDao.save(category);
    }

    @Override
    public List<Category> findAllCategory() {
	// TODO Auto-generated method stub
	return categoryDao.findAllCategory();
    }

}
