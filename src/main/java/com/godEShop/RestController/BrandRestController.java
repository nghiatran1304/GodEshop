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

import com.godEShop.Entity.Brand;
import com.godEShop.Service.BrandService;

@CrossOrigin("*")
@RestController
public class BrandRestController {

    @Autowired
    BrandService brandService;

    @GetMapping("/rest/brands")
    public List<Brand> getAll() {
	return brandService.findAll();
    }
    
    @GetMapping("/rest/brands/{name}")
    public List<Brand> getAllBrandByName(@PathVariable("name") String name){
	return brandService.getAllBrandByName("%" + name + "%");
    }
    
    @DeleteMapping("/rest/delete/{id}")
    public void deleteBrand(@PathVariable("id") Integer id) {
	brandService.delete(id);
    }

    @PutMapping("/rest/update/{id}")
    public Brand updateBrand(@PathVariable("id") Integer id, @RequestBody Brand brand) {
	return brandService.update(brand);
    }
    
    @PostMapping("/rest/create")
    public Brand createBrand(@RequestBody Brand brand) {
	return brandService.create(brand);
    }
    
}
