package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.OrderMethod;
import com.godEShop.Service.OrderMethodService;

@CrossOrigin("*")
@RestController
public class PaymentRestController {

    @Autowired
    OrderMethodService omService;
    
    @GetMapping("/rest/payments")
    public List<OrderMethod> lstOrderMethods(){
	return omService.lstOrderMethod();
    }
    
    
}
