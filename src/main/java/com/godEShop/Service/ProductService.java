package com.godEShop.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.godEShop.Entity.Product;

public interface ProductService {
    
    Page<Product> findAllByNameLike(String string, Pageable pageable);
    
    List<String> getProductAndOneImage();

    List<Product> findAll();
}
