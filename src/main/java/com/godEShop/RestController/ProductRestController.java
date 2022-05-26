package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;
    
    @GetMapping("/rest/products")
    public List<ProductShopDto> getAll() {
	return productService.findAllProduct();
    }
    
    @GetMapping("/rest/products/{productId}")
    public ProductShopDto getOne(@PathVariable("productId") Long id) {
	return productService.productShopById(id);
    }
    
 
    
}
