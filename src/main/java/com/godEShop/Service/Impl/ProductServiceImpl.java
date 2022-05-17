package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Entity.Product;
import com.godEShop.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<String> getProductAndOneImage() {
	// TODO Auto-generated method stub
	return productDAO.getProductAndOneImage();
    }

    @Override
    public List<Product> findAll() {
	// TODO Auto-generated method stub
	return productDAO.findAll();
    }

    @Override
    public Page<Product> findAllByNameLike(String string, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllByNameLike(string, pageable);
    }

    @Override
    public Page<Product> findAllProductByCategoryId(int id, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllProductByCategoryId(id, pageable);
    }

}
