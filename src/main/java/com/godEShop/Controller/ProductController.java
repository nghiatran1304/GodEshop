package com.godEShop.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Dao.ProductPhotoDAO;
import com.godEShop.Dto.AccessoryDto;
import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Dto.ProductShopDto;
import com.godEShop.Dto.WatchDto;
import com.godEShop.Entity.ProductPhoto;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.CategoryService;
import com.godEShop.Service.ProductService;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    ProductPhotoDAO productPhotoDAO;

    @GetMapping("/product")
    public String productPage1(Model model, @RequestParam("p") Optional<Integer> p,
	    @RequestParam("keywords") Optional<String> kw, @RequestParam("sort") Optional<String> sort,
	    @RequestParam("search-category") Optional<String> cName,
	    @RequestParam("search-brand") Optional<String> bName) {

	// top 2 seller
	List<ProductDiscountDto> lstTopSeller = new ArrayList<>();
	for (int i = 0; i < 3; i++) {
	    lstTopSeller.add(productService.productBestSeller().get(i));
	}
	model.addAttribute("lstTopSeller", lstTopSeller);

	// ---------------
	Pageable pageable = PageRequest.of(p.orElse(0), 12);

	if (!sort.isPresent()) {
	    pageable = PageRequest.of(p.orElse(0), 12);
	    model.addAttribute("sortChose", "a");
	} else if (sort.get().equalsIgnoreCase("discount")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("pd.discount").descending());
	    model.addAttribute("sortChose", "b");
	    model.addAttribute("sortSelected", sort.get());
	} else if (sort.get().equalsIgnoreCase("rating")) {
	    pageable = PageRequest.of(p.orElse(0), 12, JpaSort.unsafe("CAST(AVG(pe.evaluation) AS int)").descending());
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
	if (cName.isPresent()) {
	    categoryName = cName.get();
	}
	model.addAttribute("cateName", categoryName);

	String brandName = "";
	if (bName.isPresent()) {
	    brandName = bName.get();
	}
	model.addAttribute("brandName", brandName);

	String s1 = kwords.length() == 0 ? "" : " > " + kwords;
	String s2 = categoryName.length() == 0 ? "" : " > " + categoryName;
	String s3 = brandName.length() == 0 ? "" : " > " + brandName;

	model.addAttribute("nameOfSearch", (s1 + s2 + s3));

	Page<ProductShopDto> page = productService.productShop("%" + kwords + "%", "%" + categoryName + "%",
		"%" + brandName + "%", pageable);
	
	Date d = new Date();
	
	model.addAttribute("page", page);

	model.addAttribute("timeNow", d);

	model.addAttribute("totalProducts", page.getTotalElements());
	model.addAttribute("toProduct", page.getNumber());
	model.addAttribute("totalPage", page.getPageable().getPageSize());

	return "product/product";
    }

    @GetMapping("/product/{id}")
    public String singleproductPage(Model model, @PathVariable("id") Long id) {

	Date d = new Date();

	model.addAttribute("timeNow", d);

	List<ProductPhoto> lstPhotoByProductId = productPhotoDAO.getAllProductPhotoByProductId(id);
	model.addAttribute("lstPhotoByProductId", lstPhotoByProductId);

	List<ProductShopDto> pItems = productService.productShopById(id);
	ProductShopDto productItem = pItems.get(pItems.size() - 1);
//	ProductShopDto productItem = productService.productShopById(id);
	model.addAttribute("productItem", productItem);

	if (productItem.getProductCategoryId() == 13) {
	    AccessoryDto accessoryDto = productService.getAccessoryDtoById(id);
	    model.addAttribute("watchDetail", accessoryDto);
	    model.addAttribute("isWatch", false);
	} else {
	    WatchDto watchDto = productService.getWatchById(id);
	    model.addAttribute("watchDetail", watchDto);
	    model.addAttribute("isWatch", true);
	}
	List<ProductDiscountDto> lstRd = new ArrayList<>();
	int maxLength = productService.productByIdBrands(productItem.getProductCategoryId()).size() > 5 ? 5 : productService.productByIdBrands(productItem.getProductCategoryId()).size();
	for (int i = 0; i < maxLength; i++) {
	    lstRd.add(productService.productByIdBrands(productItem.getProductCategoryId()).get(i));
	}
	model.addAttribute("lstRelateProduct", lstRd);

	return "product/single-product";
    }

}
