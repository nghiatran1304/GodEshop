package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

}
