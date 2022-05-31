package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductPhotoDAO;
import com.godEShop.Entity.ProductPhoto;
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

    @Override
    public List<ProductPhoto> getAllProductPhotoByProductId(Long id) {
	// TODO Auto-generated method stub
	return productPhotoDAO.getAllProductPhotoByProductId(id);
    }

    @Override
    public ProductPhoto getById(String id) {
	// TODO Auto-generated method stub
	return productPhotoDAO.getById(id);
    }

    @Override
    public ProductPhoto update(ProductPhoto pp) {
	// TODO Auto-generated method stub
	return productPhotoDAO.save(pp);
    }

    @Override
    public ProductPhoto create(ProductPhoto pp) {
	// TODO Auto-generated method stub
	return productPhotoDAO.save(pp);
    }

}
