package com.godEShop.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Dto.ProductShopDto;
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

    @GetMapping("/product")
    public String productPage1(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw, @RequestParam("sort") Optional<String> sort,
	    @RequestParam("search-category") Optional<String> cName,
	    @RequestParam("search-brand") Optional<String> bName) {
	
	Pageable pageable = PageRequest.of(p.orElse(0), 12);
	
	if (!sort.isPresent()) {
	    pageable = PageRequest.of(p.orElse(0), 12);
	    model.addAttribute("sortChose", "a");
	} else if (sort.get().equalsIgnoreCase("discount")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("pd.discount").descending());
	    model.addAttribute("sortChose", "b");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("rating")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("pe.evaluation").descending());
	    model.addAttribute("sortChose", "c");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("newest")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("createDate").descending());
	    model.addAttribute("sortChose", "d");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("lowtohigh")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("price").ascending());
	    model.addAttribute("sortChose", "e");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("hightolow")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("price").descending());
	    model.addAttribute("sortChose", "f");
	    model.addAttribute("sortSelected", sort.get());
	}

	String kwords = "";
	if (kw.isPresent()) {
	    kwords = kw.get();
	}
	
	String categoryName = "";
	if(cName.isPresent()) {
	    categoryName = cName.get();
	}
	model.addAttribute("cateName", categoryName);
	
	String brandName = "";
	if(bName.isPresent()) {
	    brandName = bName.get();
	}
	model.addAttribute("brandName", brandName);
	
	Page<ProductShopDto> page = productService.productShop("%" + kwords + "%", "%" + categoryName + "%", "%" + brandName + "%", pageable);

	model.addAttribute("page", page);
	
	return "product/product";
    }

    

    @GetMapping("/singleproduct")
    public String singleproductPage() {
	return "product/single-product";
    }

}








































