package com.godEShop.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.godEShop.Dto.ProductDiscountDto;
import com.godEShop.Entity.ProductDiscount;
import com.godEShop.Service.BrandService;
import com.godEShop.Service.ProductDiscountService;
import com.godEShop.Service.ProductPhotoService;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.UserService;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductPhotoService productPhotoService;

    @Autowired
    ProductDiscountService productDiscountService;

    @Autowired
    BrandService brandService;

    @Autowired
    HttpServletRequest request;

    public void GetProductDiscount(Model model) {
	List<ProductDiscountDto> lstProductDiscountDto1 = productService.productDealOfTheDay();
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//	String d = simpleDateFormat.format(new Date());	
	model.addAttribute("lstTop10ProductDeal", lstProductDiscountDto1);
    }

    public void bestSeller(Model model) {
	List<ProductDiscountDto> lstProductDiscountDto2 = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    lstProductDiscountDto2.add(productService.productBestSeller().get(i));
	}
	model.addAttribute("lstBestSeller", lstProductDiscountDto2);
    }

    public void newProducts(Model model) {
	List<ProductDiscountDto> lstProductDiscountDto3 = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    lstProductDiscountDto3.add(productService.productNewArrivals().get(i));
	}

	model.addAttribute("lstNewProducts1", lstProductDiscountDto3);
    }

    public void top4Brand(Model model) {
	List<ProductDiscountDto> lstRolex = new ArrayList<>();
	List<ProductDiscountDto> lstCasio = new ArrayList<>();
	List<ProductDiscountDto> lstGShock = new ArrayList<>();
	List<ProductDiscountDto> lstSeiko = new ArrayList<>();

	for (int i = 0; i < 4; i++) {
	    lstRolex.add(productService.productByIdBrands(15).get(i));
	    lstCasio.add(productService.productByIdBrands(5).get(i));
	    lstGShock.add(productService.productByIdBrands(10).get(i));
	    lstSeiko.add(productService.productByIdBrands(14).get(i));
	}
	model.addAttribute("lstRolex", lstRolex);
	model.addAttribute("lstCasio", lstCasio);
	model.addAttribute("lstGShock", lstGShock);
	model.addAttribute("lstSeiko", lstSeiko);

    }

    @GetMapping("/index")
    public String index(Model model) {
	GetProductDiscount(model); // sản phẩm giảm giá
	bestSeller(model); // sản phẩm bán chạy nhất
	newProducts(model); // sản phẩm mới nhất
	top4Brand(model); // top 4 thương hiệu được đánh giá cao
	
	List<ProductDiscount> pd = productDiscountService.findAll();
	for(int i = 0; i < pd.size(); i++) {
	    if(pd.get(i).getEndDate().compareTo(new Date()) == -1) {
		productDiscountService.delete(pd.get(i).getId());
	    }
	}
	
	if (request.isUserInRole("Admin")) {
	    return "redirect:/admin";
	}
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

    @GetMapping("/livestream")
    public String livestream() {
	return "livestream/livestream";
    }

    @GetMapping("/room/{room_id}")
    public String streamingpage(@PathVariable("room_id") String room_id) {
	return "livestream/room";
    }

    @GetMapping("/ErrorPage")
    public String errorPage() {
	return "errorPage/ErrorPage";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//	return "account/login";
//    }

    @GetMapping("/register")
    public String registerPage() {
	return "account/register";
    }

    @Autowired
    UserService userService;

//    @GetMapping("/information")
//    public String informationPage(Authentication auth, Model model) {
//	String username = auth.getName();
//
//	User user = userService.findByUsername(username);
//
//	model.addAttribute("user", user);
//	return "account/information";
//    }

    @GetMapping("/forgotpassword")
    public String forgotpasswordPage() {
	return "account/forgot-password";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
	return "order/checkout";
    }

}
