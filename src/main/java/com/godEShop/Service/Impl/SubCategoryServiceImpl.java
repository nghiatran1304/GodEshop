package com.godEShop.Service.Impl;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.SubCategoryDAO;
import com.godEShop.Entity.SubCategory;
=======
import org.springframework.stereotype.Service;

>>>>>>> 06a770f805e880e4ea346e8c8bc2fd93707f81b6
import com.godEShop.Service.SubCategoryService;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

<<<<<<< HEAD
    @Autowired
    SubCategoryDAO subCategoryDAO;
    
    @Override
    public List<SubCategory> findAll() {
	// TODO Auto-generated method stub
	return subCategoryDAO.findAll();
    }

=======
>>>>>>> 06a770f805e880e4ea346e8c8bc2fd93707f81b6
}
