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

import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Entity.Brand;
import com.godEShop.Entity.Category;
import com.godEShop.Entity.Product;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.CategoryService;
import com.godEShop.Service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;
    
    public static Product productInserted = new Product();

    
    @GetMapping("/rest/get-products/{id}")
    public Product getProductInfo(@PathVariable("id") Long id) {
	return productService.getById(id);
    }
    
    @GetMapping("/rest/products/{productId}")
    public ProductShopDto getOne(@PathVariable("productId") Long id) {
	return productService.productShopById1(id);
    }

    @GetMapping("/rest/products")
    public List<ProductWatchInfoDto> getAll() {
	return productService.lstFullInfoWatch();
    }
    
    @GetMapping("/rest/outOfSoon")
    public List<ProductWatchInfoDto> getAllOutOfSoon(){
	return productService.lstFullInfoWatchOutOfSoon();
    }
    
    
    @GetMapping("/rest/products/search/{name}")
    public List<ProductWatchInfoDto> findBySearch(@PathVariable("name") String name) {
	return productService.lstSearchFullInfoWatch("%" + name + "%");
    }


    @PostMapping("/rest/products")
    public Product create(@RequestBody ProductWatchInfoDto product) throws Exception{
	System.out.println(product == null ? " >>>> NULL PRODUCT" : " >>>>> NOT NULL PRODUCT");
	System.out.println(product.getCategoryId() == null ? " >>>> NULL CATEGORY ID " : " >>>>> NOT NULL CATEGORY ID : " + product.getCategoryId());
	System.out.println(product.getBrandId() == null ? " >>>> NULL BRAND ID" : " >>>>> NOT NULL BRAND ID: " + product.getBrandId());
	
	Category c = categoryService.getById(product.getCategoryId());
	Brand b = brandService.getById(product.getBrandId());
	Product p = new Product();
	p.setCreateDate(product.getProductCreateDate());
	p.setDetail(product.getProductDetail());
	p.setMadeIn(product.getProductMadeIn());
	p.setName(product.getProductName());
	p.setIsDeleted(product.getProductIsDeteled());
	p.setPrice(product.getProductPrice());
	p.setQuantity(product.getProductQuantity());
	p.setWarranty(product.getProductWarranty());
	p.setBrand(b);
	p.setCategory(c);
	
	productInserted = p;
	
	return productService.create(p);
    }

    @PutMapping("/rest/products/{id}")
    public Product update(@PathVariable("id") Long id, @RequestBody ProductWatchInfoDto product) {
	Brand b = brandService.getById(product.getBrandId());
	Category c = categoryService.getById(product.getCategoryId());
	Product p = new Product();
	p.setId(product.getProductId());
	p.setCreateDate(product.getProductCreateDate());
	p.setDetail(product.getProductDetail());
	p.setMadeIn(product.getProductMadeIn());
	p.setName(product.getProductName());
	p.setIsDeleted(product.getProductIsDeteled());
	p.setPrice(product.getProductPrice());
	p.setQuantity(product.getProductQuantity());
	p.setWarranty(product.getProductWarranty());
	p.setBrand(b);
	p.setCategory(c);

	return productService.update(p);
    }

    @DeleteMapping("/rest/delete/products/{id}")
    public void delete(@PathVariable("id") Long id) {
	productService.delete(id);
    }

}
