package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Dto.ProductAccessoryInfoDto;
import com.godEShop.Entity.Accessory;
import com.godEShop.Entity.BraceletMaterial;
import com.godEShop.Entity.Brand;
import com.godEShop.Entity.Category;
import com.godEShop.Entity.Product;
import com.godEShop.Entity.ProductPhoto;
import com.godEShop.Service.AccessoryService;
import com.godEShop.Service.BraceletMaterialService;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.CategoryService;
import com.godEShop.Service.ProductPhotoService;
import com.godEShop.Service.ProductService;

@CrossOrigin("*")
@RestController
public class AccessoryRestController {
	@Autowired
	AccessoryService accessoryService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	BrandService brandService;
	@Autowired
	ProductService productService;
	@Autowired
	BraceletMaterialService bmService;
	@Autowired
	ProductPhotoService productPhotoService;

	public static Product productInserted1 = new Product();

	@GetMapping("/rest/accessories")
	public List<ProductAccessoryInfoDto> getAll() {
		return accessoryService.lstFullInfoAccessory();
	}

	@PostMapping("/rest/post-product")
	public Product create(@RequestBody ProductAccessoryInfoDto product) {
		System.out.println(product == null ? " >>>> NULL PRODUCT" : " >>>>> NOT NULL PRODUCT");
		System.out.println(product.getCategoryId() == null ? " >>>> NULL CATEGORY ID "
				: " >>>>> NOT NULL CATEGORY ID : " + product.getCategoryId());
		System.out.println(product.getBrandId() == null ? " >>>> NULL BRAND ID"
				: " >>>>> NOT NULL BRAND ID: " + product.getBrandId());

		Category c = categoryService.getById(13);
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

		productInserted1 = p;

		return productService.create(p);
	}
	
	@PostMapping("/rest/post-accessory")
	public Accessory createAccessory(@RequestBody ProductAccessoryInfoDto product) {
		System.out.println(
				productInserted1 == null ? " >>> NULL PRODUCT RESTCONTROLLER" : " >>> NOT NULL PRODUCT RESTCONTROLLER");
		System.out
				.println(product == null ? " >>> NULL PRODUCT RESTCONTROLLER" : " >>> NOT NULL PRODUCT RESTCONTROLLER");
		BraceletMaterial bm = bmService.getById(product.getBraceletMaterialId());
		Accessory ac = new Accessory();
		ac.setColors(product.getAccessoryColor());
		ac.setBraceletMaterial(bm);
		ac.setProduct(productInserted1);
		return accessoryService.create(ac);
	}
    @PutMapping("/rest/put-product/{id}")
    public Product update(@PathVariable("id") Long id, @RequestBody ProductAccessoryInfoDto product) {
	Brand b = brandService.getById(product.getBrandId());
	Category c = categoryService.getById(13);
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
	@PutMapping("/rest/accessory/{id}")
	public Accessory update(@PathVariable("id") Integer id, @RequestBody ProductAccessoryInfoDto product) {
		BraceletMaterial bm = bmService.getById(product.getBraceletMaterialId());
		Accessory ac = accessoryService.getById(product.getAccessoryId());
		ac.setBraceletMaterial(bm);
		ac.setColors(product.getAccessoryColor());
		ac.setProduct(ac.getProduct());
		return accessoryService.update(ac);
	}

	// them moi san pham
	@PostMapping("/rest/post-photo")
	public ProductPhoto update1(@RequestBody ProductAccessoryInfoDto product) {
		ProductPhoto pp = new ProductPhoto();
		pp.setId(product.getImageId());
		pp.setProduct(ProductRestController.productInserted);
		return productPhotoService.create(pp);
	}

	// up them hinh
	@PostMapping("/rest/post-photo-plus")
	public ProductPhoto update(@RequestBody ProductAccessoryInfoDto product) {
		ProductPhoto pp = new ProductPhoto();
		pp.setId(product.getImageId());
		pp.setProduct(productService.getById(product.getProductId()));
		return productPhotoService.create(pp);
	}
	
	 @GetMapping("/rest/products-accessory/search/{name}")
	    public List<ProductAccessoryInfoDto> findBySearch(@PathVariable("name") String name) {
		return accessoryService.lstSearchFullInfoAccessory("%" + name + "%");
	    }
}
