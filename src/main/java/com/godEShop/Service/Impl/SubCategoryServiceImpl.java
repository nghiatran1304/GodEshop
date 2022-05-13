package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.SubCategoryDAO;
import com.godEShop.Entity.SubCategory;
import com.godEShop.Service.SubCategoryService;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    SubCategoryDAO subCategoryDAO;

    @Override
    public List<SubCategory> findAll() {
	// TODO Auto-generated method stub
	return subCategoryDAO.findAll();
    }
}
