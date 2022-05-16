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
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Entity.Product;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.SessionService;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    SessionService session;

    @GetMapping("/product")
    public String productPage(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw) {
	List<String> lstImage = productService.getProductAndOneImage(); // danh sách hình đầu tiên mỗi sản phẩm
	List<Product> lstProducts = productService.findAll(); // danh sách các sản phẩm

	String kwords = "";
	if(kw.isPresent()) {
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
	int fromProduct = (mapProducts.size() * ( p.orElse(0) >= 1 ? p.get() + 1 : 1 )) - page.getNumberOfElements() + 1;
	model.addAttribute("fromProduct", fromProduct );
	int toProduct = mapProducts.size() * ( p.orElse(0) >= 1 ? p.get() + 1 : 1 );
	model.addAttribute("toProduct", toProduct );
	
	return "product/product";

    }

    @GetMapping("/product/category/{id}")
    public String productPageByCategory() {
	return "product/product";

    }

    @GetMapping("/singleproduct")
    public String singleproductPage() {
	return "product/single-product";
    }

}
