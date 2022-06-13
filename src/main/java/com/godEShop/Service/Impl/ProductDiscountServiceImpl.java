package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductDiscountDAO;
import com.godEShop.Entity.ProductDiscount;
import com.godEShop.Service.ProductDiscountService;

@Service
public class ProductDiscountServiceImpl implements ProductDiscountService{

    @Autowired
    ProductDiscountDAO productDiscountDAO;    
    
    @Override
    public ProductDiscount getProductDiscount(Long productId) {
	// TODO Auto-generated method stub
	return productDiscountDAO.getProductDiscount(productId);
    }

    @Override
    public List<ProductDiscount> findAll() {
	// TODO Auto-generated method stub
	return productDiscountDAO.findAll();
    }

    @Override
    public void delete(Integer id) {
	// TODO Auto-generated method stub
	productDiscountDAO.deleteById(id);
    }


}
