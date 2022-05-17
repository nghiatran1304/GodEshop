package com.godEShop.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Entity.Product;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.CategoryService;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.SessionService;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    BrandService brandService;

    @Autowired
    SessionService session;

    public void changedPagination(Model model, int choose, String nameOfSearch) {
	int whichPagination = choose;
	model.addAttribute("whichPagination", whichPagination);
	model.addAttribute("nameOfSearch", nameOfSearch);

    }

    // sản phẩm
    @GetMapping("/product")
    public String productPage(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw) {

	if (p.isPresent()) {
	    if (p.get() < 0) {
		p = Optional.of(0);
	    }
	}

	changedPagination(model, 1, "All Product"); // by product
	List<String> lstImage = productService.getProductAndOneImage(); // danh sách hình đầu tiên mỗi sản phẩm
	List<Product> lstProducts = productService.findAll(); // danh sách các sản phẩm

	String kwords = "";
	if (kw.isPresent()) {
	    kwords = kw.get();
	}

	Pageable pageable = PageRequest.of(p.orElse(0), 12);

	Page<Product> page = productService.findAllByNameLike("%" + kwords + "%", pageable);

	Map<String, Product> mapProducts = new HashMap<>();

	for (int i = 0; i < page.getContent().size(); i++) {
	    int index = lstProducts.indexOf(page.getContent().get(i));
	    mapProducts.put(lstImage.get(index), page.getContent().get(i));
	}

	model.addAttribute("mapProducts", mapProducts);
	model.addAttribute("page", page);

	int totalProducts = kwords == "" ? lstProducts.size() : page.getNumberOfElements();
	model.addAttribute("totalProducts", totalProducts);
	int fromProduct = (mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1)) - page.getNumberOfElements() + 1;
	model.addAttribute("fromProduct", fromProduct);
	int toProduct = mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1);
	model.addAttribute("toProduct", toProduct);

	return "product/product";

    }

    // sản phẩm theo danh mục
    @GetMapping("/product/category/{id}")
    public String productPageByCategory(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw, @PathVariable("id") Integer id) {
	
	if (p.isPresent()) {
	    if (p.get() < 0) {
		p = Optional.of(0);
	    }
	}

	changedPagination(model, 2, "Category : " + categoryService.getById(id).getName()); // by category

	model.addAttribute("idCategory", id);
	List<String> lstImage = productService.getProductAndOneImage(); // danh sách hình đầu tiên mỗi sản phẩm
	List<Product> lstProducts = productService.findAll(); // danh sách các sản phẩm

	String kwords = "";
	if (kw.isPresent()) {
	    kwords = kw.get();
	}

	Pageable pageable = PageRequest.of(p.orElse(0), 12);

	Page<Product> page = productService.findAllProductByCategoryId(id, pageable);

	Map<String, Product> mapProducts = new HashMap<>();

	for (int i = 0; i < page.getContent().size(); i++) {
	    int index = lstProducts.indexOf(page.getContent().get(i));
	    mapProducts.put(lstImage.get(index), page.getContent().get(i));
	}

	model.addAttribute("mapProducts", mapProducts);
	model.addAttribute("page", page);

	int totalProducts = kwords == "" ? lstProducts.size() : page.getNumberOfElements();
	model.addAttribute("totalProducts", totalProducts);
	int fromProduct = (mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1)) - page.getNumberOfElements() + 1;
	model.addAttribute("fromProduct", fromProduct);
	int toProduct = mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1);
	model.addAttribute("toProduct", toProduct);

	return "product/product";

    }
    
    
    // sản phẩm theo thương hiệu
    @GetMapping("/product/brand/{id}")
    public String productPageByBrand(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw, @PathVariable("id") Integer id) {
	
	if (p.isPresent()) {
	    if (p.get() < 0) {
		p = Optional.of(0);
	    }
	}

	changedPagination(model, 3, "Brands : " + brandService.getById(id).getName()); // by category

	model.addAttribute("idBrand", id);
	List<String> lstImage = productService.getProductAndOneImage(); // danh sách hình đầu tiên mỗi sản phẩm
	List<Product> lstProducts = productService.findAll(); // danh sách các sản phẩm

	String kwords = "";
	if (kw.isPresent()) {
	    kwords = kw.get();
	}

	Pageable pageable = PageRequest.of(p.orElse(0), 12);

	Page<Product> page = productService.findAllProductByBrandId(id, pageable);

	Map<String, Product> mapProducts = new HashMap<>();

	for (int i = 0; i < page.getContent().size(); i++) {
	    int index = lstProducts.indexOf(page.getContent().get(i));
	    mapProducts.put(lstImage.get(index), page.getContent().get(i));
	}

	model.addAttribute("mapProducts", mapProducts);
	model.addAttribute("page", page);

	int totalProducts = kwords == "" ? lstProducts.size() : page.getNumberOfElements();
	model.addAttribute("totalProducts", totalProducts);
	int fromProduct = (mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1)) - page.getNumberOfElements() + 1;
	model.addAttribute("fromProduct", fromProduct);
	int toProduct = mapProducts.size() * (p.orElse(0) >= 1 ? p.get() + 1 : 1);
	model.addAttribute("toProduct", toProduct);

	return "product/product";

    }
    
    @GetMapping("/singleproduct")
    public String singleproductPage() {
	return "product/single-product";
    }

}
