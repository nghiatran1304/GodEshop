package com.godEShop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductPhotoDAO;
import com.godEShop.Service.ProductPhotoService;

@Service
public class ProductPhotoServiceImpl implements ProductPhotoService {

    @Autowired
    ProductPhotoDAO productPhotoDAO;
    
    @Override
    public String productFirstPhotoname(Long productId) {
	// TODO Auto-generated method stub
	return productPhotoDAO.productFirstPhotoname(productId);
    }

}
