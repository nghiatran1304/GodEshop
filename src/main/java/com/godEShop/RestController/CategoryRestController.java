package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @GetMapping("/rest/categoriesAvailable")
    public List<Category> getAllAvailable() {
	return categoryService.findAllCategory();
    }
    
    @GetMapping("/rest/categories/{name}")
    public List<Category> getAllBrandByName(@PathVariable("name") String name) {
	return categoryService.getAllCateoryByName("%" + name + "%");
    }

    @DeleteMapping("/rest/categories/delete/{id}")
    public void deleteBrand(@PathVariable("id") Integer id) {
	categoryService.delete(id);
    }

    @PutMapping("rest/categories/update/{id}")
    public Category updateBrand(@PathVariable("id") Integer id, @RequestBody Category category) {
	return categoryService.update(category);
    }

    @PostMapping("rest/categories/create")
    public Category createBrand(@RequestBody Category category) {
	return categoryService.create(category);
    }
    
}
