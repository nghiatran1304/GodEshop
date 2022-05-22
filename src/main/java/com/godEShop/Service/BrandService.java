package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.Brand;

public interface BrandService {
    
    List<Brand> findAll();
    
    List<Integer> getTop4BrandByEvaluation();
    
    Brand getById(int id);
}
