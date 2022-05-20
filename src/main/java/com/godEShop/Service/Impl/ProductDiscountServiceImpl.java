package com.godEShop.Service.Impl;

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


}
