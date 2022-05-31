package com.godEShop.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dto.ProductWatchInfoDto;
import com.godEShop.Entity.ProductPhoto;
import com.godEShop.Service.ProductPhotoService;
import com.godEShop.Service.ProductService;

@CrossOrigin("*")
@RestController
public class ProductPhotoRestController {

    @Autowired
    ProductPhotoService productPhotoService;

    @Autowired
    ProductService productService;

    @PutMapping("/rest/photo/{id}")
    public ProductPhoto update(@PathVariable("id") String id, @RequestBody ProductWatchInfoDto product) {
//	ProductPhoto pp = productPhotoService.getById(id);
	ProductPhoto pp = new ProductPhoto();
	pp.setId(product.getImageId());
	pp.setProduct(productService.getById(product.getProductId()));
	return productPhotoService.update(pp);
    }

    @PostMapping("/rest/photo")
    public ProductPhoto update(@RequestBody ProductWatchInfoDto product) {
//	ProductPhoto pp = productPhotoService.getById(id);
	ProductPhoto pp = new ProductPhoto();
	pp.setId(product.getImageId());
//	Product p = sessionService.get("productInsert");
	pp.setProduct(ProductRestController.productInserted);
	return productPhotoService.create(pp);
    }

}
