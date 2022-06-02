package com.godEShop.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.godEShop.Service.OrderService;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/cart")
    public String view() {
	return "cart/view-cart";
    }

    @RequestMapping("/order/list")
	public String list(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}
}
