package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.Category;
import com.godEShop.Service.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;
    
    @GetMapping("/rest/categories")
    public List<Category> getAll() {
	return categoryService.findAll();
    }
    
}
