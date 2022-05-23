package com.godEShop.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Entity.Product;

public interface ProductService {

    Product getById(Long productId);

    Page<Product> findAllByNameLike(String string, Pageable pageable);

    Page<Product> findAllProductByCategoryId(int id, Pageable pageable);

    Page<Product> findAllProductByBrandId(int id, Pageable pageable);

    Page<Product> findAllPriceAsc(String keywords, Pageable pageable);

    Page<Product> findAllPriceDesc(String keywords, Pageable pageable);

    Page<Product> findAllNewProduct(String keywords, Pageable pageable);

    List<Product> findAll();

    List<Product> getAllNewProducts();

    List<Product> findAllByCategoryId(int id);
    
    //-------------------------
    Page<ProductShopDto> productShop(String kws, String categoryName, String brandName,Pageable pageable);

    //-------------------------
    List<ProductDiscountDto> productDealOfTheDay();
    
    //-------------------------
    List<ProductDiscountDto> productBestSeller();
    
    //-------------------------
    List<ProductDiscountDto> productNewArrivals();
    
    //-------------------------
    List<ProductDiscountDto> productByIdBrands(Integer id);
}
