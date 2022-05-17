package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.Category;

public interface CategoryService {
    List<Category> findAll();
    
    Category getById(int id);
}
