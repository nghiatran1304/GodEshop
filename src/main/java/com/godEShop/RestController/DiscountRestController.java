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

import com.godEShop.Dto.ProductOnSaleDto;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductDiscount;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.DiscountService;
import com.godEShop.Service.ProductService;

@CrossOrigin("*")
@RestController
public class DiscountRestController {
    
    @Autowired
    DiscountService discountService;
    
    @Autowired
    ProductService productService;
    
    @Autowired
    AccountService accountService;
    
    @GetMapping("/rest/product-none-discount")
    public List<Product> productNoneDiscount(){
	return discountService.getProductNonDiscount();
    }
    
    @GetMapping("/rest/product-onsale")
    public List<ProductOnSaleDto> productOnSale(){
	return discountService.getProductOnSale();
    }
    
    @PostMapping("/rest/create-discount")
    public ProductDiscount create(@RequestBody ProductOnSaleDto po) {
	ProductDiscount pd = new ProductDiscount();
	pd.setCreateDate(po.getProductDiscountCreateDate());
	pd.setEndDate(po.getProductDiscountEndDate());
	pd.setDiscount(po.getProductDiscountPercent());
	pd.setAccount(accountService.findByUsername(po.getUsername()));
	pd.setProduct(productService.getById(po.getProductId()));
	
	return discountService.create(pd);
    }
    
    @DeleteMapping("/rest/delete-discount/{id}")
    public void deleteBrand(@PathVariable("id") Integer id) {
	discountService.delete(id);
    }

    @PutMapping("rest/update-discount/{id}")
    public ProductDiscount update(@PathVariable("id") Integer id, @RequestBody ProductOnSaleDto po) {
	ProductDiscount pd = new ProductDiscount();
	pd.setId(po.getProductDiscountId());
	pd.setCreateDate(po.getProductDiscountCreateDate());
	pd.setEndDate(po.getProductDiscountEndDate());
	pd.setDiscount(po.getProductDiscountPercent());
	pd.setAccount(accountService.findByUsername(po.getUsername()));
	pd.setProduct(productService.getById(po.getProductId()));
	return discountService.update(pd);
    }

 

    
}
