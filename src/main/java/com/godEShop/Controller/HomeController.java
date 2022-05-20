package com.godEShop.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Entity.Product;
import com.godEShop.Service.ProductDiscountService;
import com.godEShop.Service.ProductPhotoService;
import com.godEShop.Service.ProductService;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductPhotoService productPhotoService;

    @Autowired
    ProductDiscountService productDiscountService;

    public void GetProductDiscont(Model model) {
	List<ProductDiscountDto> lstProductDiscountDto = new ArrayList<>();

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

	List<Product> lstTop10ProductDeal = productService.getTop10ProductDeal();

	for (int i = 0; i < lstTop10ProductDeal.size(); i++) {
	    double ds = lstTop10ProductDeal.get(i).getPrice()
		    * productDiscountService.getProductDiscount(lstTop10ProductDeal.get(i).getId()).getDiscount()
		    / 100.0;
	    double rs = lstTop10ProductDeal.get(i).getPrice() - ds;
	    ProductDiscountDto pdDto = new ProductDiscountDto();
	    pdDto.setProductId(lstTop10ProductDeal.get(i).getId());
	    pdDto.setProductName(lstTop10ProductDeal.get(i).getName());
	    pdDto.setProductPrice(String.format("%.2f", lstTop10ProductDeal.get(i).getPrice()));
	    pdDto.setProductPriceAfterDiscount(String.format("%.2f", rs));
	    pdDto.setEndDate(simpleDateFormat.format(
		    productDiscountService.getProductDiscount(lstTop10ProductDeal.get(i).getId()).getEndDate()));
	    pdDto.setProductImage(productPhotoService.productFirstPhotoname(lstTop10ProductDeal.get(i).getId()));
	    pdDto.setProductDetail(lstTop10ProductDeal.get(i).getDetail());
	    lstProductDiscountDto.add(pdDto);

	}

	model.addAttribute("lstTop10ProductDeal", lstProductDiscountDto);
    }

    public void bestSeller(Model model) {
	List<Long> lstProductId = productService.getTop10BestSellers();
	List<ProductDiscountDto> lstProductDiscountDto = new ArrayList<>();
	for (int i = 0; i < lstProductId.size(); i++) {
	    ProductDiscountDto pdDto = new ProductDiscountDto();
	    Product p = productService.getById(lstProductId.get(i));
	    pdDto.setProductId(p.getId());
	    pdDto.setProductName(p.getName());
	    pdDto.setProductPrice(String.format("%.2f", p.getPrice()));
	    pdDto.setProductPriceAfterDiscount("");
	    pdDto.setEndDate("");
	    pdDto.setProductImage(productPhotoService.productFirstPhotoname(lstProductId.get(i)));
	    pdDto.setProductDetail(p.getDetail());
	    lstProductDiscountDto.add(pdDto);
	}
	model.addAttribute("lstBestSeller", lstProductDiscountDto);
    }

    public void newProducts(Model model) {
	List<ProductDiscountDto> lstProductDiscountDto1 = new ArrayList<>();
	List<Product> lstProduct = productService.getAllNewProducts();
	for (int i = 0; i < (lstProduct.size() / 2); i++) {
	    ProductDiscountDto pdDto = new ProductDiscountDto();
	    pdDto.setProductId(lstProduct.get(i).getId());
	    pdDto.setProductName(lstProduct.get(i).getName());
	    pdDto.setProductPrice(String.format("%.2f", lstProduct.get(i).getPrice()));
	    pdDto.setProductPriceAfterDiscount("");
	    pdDto.setEndDate("");
	    pdDto.setProductImage(productPhotoService.productFirstPhotoname(lstProduct.get(i).getId()));
	    pdDto.setProductDetail(lstProduct.get(i).getDetail());

	    lstProductDiscountDto1.add(pdDto);
	}
	
	model.addAttribute("lstNewProducts1", lstProductDiscountDto1);
    }

    @GetMapping("/index")
    public String index(Model model) {
	GetProductDiscont(model); // sản phẩm giảm giá
	bestSeller(model); // sản phẩm bán chạy nhất
	newProducts(model); // sản phẩm mới nhất
	return "layout/homepage";
    }

    @GetMapping("/about")
    public String aboutus() {
	return "about/AboutUs";
    }

    @GetMapping("/blog")
    public String blog() {
	return "blog/Blog";
    }

    @GetMapping("/contact")
    public String contact() {
	return "contact/Contact";
    }

    @GetMapping("/ErrorPage")
    public String errorPage() {
	return "errorPage/ErrorPage";
    }

    @GetMapping("/login")
    public String loginPage() {
	return "account/login";
    }

    @GetMapping("/register")
    public String registerPage() {
	return "account/register";
    }

    @GetMapping("/information")
    public String informationPage() {
	return "account/information";
    }

    @GetMapping("/forgotpassword")
    public String forgotpasswordPage() {
	return "account/forgot-password";
    }

    @GetMapping("/viewcart")
    public String viewCartPage() {
	return "cart/view-cart";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
	return "order/checkout";
    }

}
