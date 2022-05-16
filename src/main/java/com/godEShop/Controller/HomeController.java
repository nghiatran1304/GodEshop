package com.godEShop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index() {
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
