package com.godEShop.Controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Config.PaypalPaymentIntent;
import com.godEShop.Config.PaypalPaymentMethod;
import com.godEShop.Service.PaypalService;
import com.godEShop.Utils.Utils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());
   
    @Autowired
    PaypalService paypalService;

    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price) {
	String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
	String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
	try {
	    Payment payment = paypalService.createPayment(price, "USD", PaypalPaymentMethod.paypal,
		    PaypalPaymentIntent.sale, "payment description", cancelUrl, successUrl);
	    for (Links links : payment.getLinks()) {
		if (links.getRel().equals("approval_url")) {
		    System.out.println(" >> pay() -> 1");
		    return "redirect:" + links.getHref();
		}
	    }
	} catch (PayPalRESTException e) {
	    log.error(e.getMessage());
	}
	System.out.println(" >> pay() -> 2");
	return "account/information";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
	System.out.println(" >>> cancelPay()");
	return "order/checkout";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
	try {
	    Payment payment = paypalService.executePayment(paymentId, payerId);
	    if (payment.getState().equals("approved")) {
		System.out.println(" >> successPay() -> 1");
		return "redirect:/information";
	    }
	} catch (PayPalRESTException e) {
	    log.error(e.getMessage());
	}
	System.out.println(" >> successPay() -> 2");
	return "order/checkout";
    }
}
