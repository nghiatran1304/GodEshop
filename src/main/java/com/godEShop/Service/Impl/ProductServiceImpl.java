package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dao.ProductLikeDAO;
import com.godEShop.Dto.AccessoryDto;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Dto.WatchDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductLike;
import com.godEShop.Service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	ProductLikeDAO productLikeDAO;
	
	@Override
	public Page<ProductShopDto> productShop(String kws, String categoryName, String brandName, Double minPrice,	Double maxPrice, int is6, int is7, int is8, int is9, Pageable pageable) {
		// TODO Auto-generated method stub
		return productDAO.productShop(kws, categoryName, brandName, minPrice, maxPrice, is6, is7, is8, is9, pageable);
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

	@Override
	public List<ProductShopDto> productShopById(Long id) {
		// TODO Auto-generated method stub
		return productDAO.productShopById(id);
	}

	@Override
	public ProductShopDto productShopById1(Long id) {
		// TODO Auto-generated method stub
		return productDAO.productShopById1(id);
	}

	@Override
	public WatchDto getWatchById(Long id) {
		// TODO Auto-generated method stub
		return productDAO.getWatchById(id);
	}

	@Override
	public AccessoryDto getAccessoryDtoById(Long id) {
		// TODO Auto-generated method stub
		return productDAO.getAccessoryDtoById(id);
	}

	@Override
	public List<ProductShopDto> findAllProduct() {
		// TODO Auto-generated method stub
		return productDAO.findAllProduct();
	}

	@Override
	public List<ProductWatchInfoDto> lstFullInfoWatch() {
		// TODO Auto-generated method stub
		return productDAO.lstFullInfoWatch();
	}

	@Override
	public Product create(Product product) {
		// TODO Auto-generated method stub
		return productDAO.save(product);
	}

	@Override
	public Product update(Product product) {
		// TODO Auto-generated method stub
		return productDAO.save(product);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Product oldProduct = productDAO.getById(id);
		oldProduct.setIsDeleted(true);
		productDAO.save(oldProduct);
	}

	@Override
	public Product getById(Long id) {
		// TODO Auto-generated method stub
		return productDAO.getById(id);
	}

	@Override
	public List<ProductWatchInfoDto> lstSearchFullInfoWatch(String name) {
		// TODO Auto-generated method stub
		return productDAO.lstSearchFullInfoWatch(name);
	}

	@Override
	public List<Product> findByNameOrderDetail(String productName) {
		// TODO Auto-generated method stub
		return productDAO.findByNameOrderDetail(productName);
	}

	@Override
	public List<ProductWatchInfoDto> lstFullInfoWatchOutOfSoon() {
	    // TODO Auto-generated method stub
	    return productDAO.lstFullInfoWatchOutOfSoon();
	}
	
	@Override
	public ProductLike getProductLikeByUsernameAndProductId(String username, Long id) {
		// TODO Auto-generated method stub
		return productLikeDAO.getProductLikeByUsernameAndProductId(username, id);
	}

	@Override
	public ProductLike createProductLike(ProductLike pl) {
		// TODO Auto-generated method stub
		return productLikeDAO.save(pl);
	}

	@Override
	public Page<ProductShopDto> productLike(  String remoteUser,Pageable pageable) {
		// TODO Auto-generated method stub
		return productDAO.productLike( remoteUser,pageable);
	}

	
}
