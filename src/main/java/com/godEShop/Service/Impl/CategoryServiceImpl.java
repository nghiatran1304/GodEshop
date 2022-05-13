package com.godEShop.Service.Impl;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.CategoryDAO;
import com.godEShop.Entity.Category;
=======
import org.springframework.stereotype.Service;

>>>>>>> 06a770f805e880e4ea346e8c8bc2fd93707f81b6
import com.godEShop.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
<<<<<<< HEAD
    
    @Autowired
    CategoryDAO categoryDAO;
    
    @Override
    public List<Category> findAll() {
	// TODO Auto-generated method stub
	return categoryDAO.findAll();
    }
=======
>>>>>>> 06a770f805e880e4ea346e8c8bc2fd93707f81b6

}
