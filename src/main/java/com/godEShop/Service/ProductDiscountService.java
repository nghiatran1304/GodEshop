package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.ProductDiscount;

public interface ProductDiscountService {

    ProductDiscount getProductDiscount(Long productId);

    List<ProductDiscount> findAll();

    void delete(Integer id);

}
