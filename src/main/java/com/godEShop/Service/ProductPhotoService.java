package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.ProductPhoto;

public interface ProductPhotoService {
    String productFirstPhotoname(Long productId);
    
    List<ProductPhoto> getAllProductPhotoByProductId(Long id);

    ProductPhoto getById(String id);

    ProductPhoto update(ProductPhoto pp);

    ProductPhoto create(ProductPhoto pp);
    
}
