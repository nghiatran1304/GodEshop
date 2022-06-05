package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.Category;

public interface CategoryService {
    List<Category> findAll();
    
    Category getById(int id);

    List<Category> getAllCateoryByName(String string);

    void delete(Integer id);

    Category update(Category category);

    Category create(Category category);

    List<Category> findAllCategory();
}
