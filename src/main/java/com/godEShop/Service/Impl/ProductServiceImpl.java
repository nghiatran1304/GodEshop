package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Entity.Product;
import com.godEShop.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

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

    @Override
    public Page<Product> findAllProductByBrandId(int id, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllProductByBrandId(id, pageable);
    }

    @Override
    public Page<Product> findAllPriceAsc(String keywords, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllPriceAsc(keywords, pageable);
    }

    @Override
    public Page<Product> findAllPriceDesc(String keywords, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllPriceDesc(keywords, pageable);
    }

    @Override
    public Page<Product> findAllNewProduct(String keywords, Pageable pageable) {
	// TODO Auto-generated method stub
	return productDAO.findAllNewProduct(keywords, pageable);
    }
    
    @Override
    public Product getById(Long productId) {
	// TODO Auto-generated method stub
	return productDAO.getById(productId);
    }

    @Override
    public List<Product> getAllNewProducts() {
	// TODO Auto-generated method stub
	return productDAO.getAllNewProducts();
    }

    @Override
    public List<Product> findAllByCategoryId(int id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Page<ProductShopDto> productShop(String kws, String categoryName, String brandName, Pageable pageable) {
	// TODO Auto-generated method stub
	return  productDAO.productShop(kws, categoryName, brandName, pageable);
    }

    @Override
    public List<ProductDiscountDto> productDealOfTheDay() {
	// TODO Auto-generated method stub
	return productDAO.productDealOfTheDay();
    }

    @Override
    public List<ProductDiscountDto> productBestSeller() {
	// TODO Auto-generated method stub
	return productDAO.productBestSeller();
    }

    @Override
    public List<ProductDiscountDto> productNewArrivals() {
	// TODO Auto-generated method stub
	return productDAO.productNewArrivals();
    }

    @Override
    public List<ProductDiscountDto> productByIdBrands(Integer id) {
	// TODO Auto-generated method stub
	return productDAO.productByIdBrands(id);
    }
    
}
