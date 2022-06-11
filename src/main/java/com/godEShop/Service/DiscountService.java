package com.godEShop.Service;

import java.util.List;

import com.godEShop.Dto.ProductOnSaleDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductDiscount;

public interface DiscountService {

    List<Product> getProductNonDiscount();
    
    List<ProductOnSaleDto> getProductOnSale();

    ProductDiscount create(ProductDiscount pd);

    void delete(Integer id);

    ProductDiscount update(ProductDiscount pd);

}
