package com.godEShop.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.godEShop.Dto.AccessoryDto;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Dto.WatchDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductLike;

public interface ProductService {

    List<ProductShopDto> findAllProduct();

    // -------------------------
    Page<ProductShopDto> productShop(String kws, String categoryName, String brandName, Double minPrice, Double maxPrice, int is6, int is7, int is8, int i9, Pageable pageable);

    // -------------------------
    List<ProductDiscountDto> productDealOfTheDay();

    // -------------------------
    List<ProductDiscountDto> productBestSeller();

    // -------------------------
    List<ProductDiscountDto> productNewArrivals();

    // -------------------------
    List<ProductDiscountDto> productByIdBrands(Integer id);

    // -------------------------
    ProductShopDto productShopById1(Long id);
    List<ProductShopDto> productShopById(Long id);

    // -------------------------
    WatchDto getWatchById(Long id);

    // -------------------------
    AccessoryDto getAccessoryDtoById(Long id);

    // -------------------------
    List<ProductWatchInfoDto> lstFullInfoWatch();

    // -------------------------
    Product create(Product product);

    Product update(Product product);

    void delete(Long id);
    
    Product getById(Long id);

    List<ProductWatchInfoDto> lstSearchFullInfoWatch(String name);
    
    List<Product> findByNameOrderDetail(String productName);

    List<ProductWatchInfoDto> lstFullInfoWatchOutOfSoon();


	ProductLike getProductLikeByUsernameAndProductId(String username , Long id);
	
	ProductLike createProductLike(ProductLike pl);

	Page<ProductShopDto> productLike(  String remoteUserr,Pageable pageable);
	
}
