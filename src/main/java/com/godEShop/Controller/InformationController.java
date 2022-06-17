package com.godEShop.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Dto.OrderCartViewDto;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Account;
import com.godEShop.Entity.MailInfo;
import com.godEShop.Entity.User;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.MailerService;
import com.godEShop.Service.OrderDetailService;
import com.godEShop.Service.OrderService;
import com.godEShop.Service.UploadService;
import com.godEShop.Service.UserService;

@Controller
public class InformationController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    ServletContext app;
    @Autowired 
    UploadService uploadService;
    @Autowired
    OrderDetailService orderDetailService;
	@Autowired
	MailerService mailerServie;
	  @Autowired
	    BCryptPasswordEncoder pe;
	static Boolean isVerificationEmail = false;
    @GetMapping("/information")
    public String informationPage(HttpServletRequest request, Model model) {
	String username = request.getRemoteUser();
	User user = userService.findByUsername(username);

	if (user.getPhoto().contains("jpg")||user.getPhoto().contains("png")) {
	    model.addAttribute("isOAuth2", false);
	} else {
	    model.addAttribute("isOAuth2", true);
	}
	if(isVerificationEmail==true) {
		model.addAttribute("isVerificationEmail", "true");
	}else {
		model.addAttribute("isVerificationEmail", "false");
	}
	model.addAttribute("user", user);

	List<OrderListDto> orders = orderService.findByUsername1(username);
	if (orders.size() != 0) {
	    model.addAttribute("orders", orders);
	} else {
	    model.addAttribute("message", "is not have any orders before here");
	}
//	
//	List<OrderCartViewDto> orderDetails = orderDetailService.findById(id);
//	  model.addAttribute("orderDetails", orderDetails);
	return "account/information";
    }
    @PostMapping("/update/accountDetail")
    public String updateAccountDetail(Model model, HttpServletRequest request,
	    @RequestParam("fullname") String fullname, @RequestParam("email") String email,
	    @RequestParam("gender") Integer gender, @RequestParam("dob") String dob,
	    @RequestParam("address") String address,@RequestParam("phone") String phone,
	    @RequestParam("photo") MultipartFile photo) throws ParseException {
    	
    		String username = request.getRemoteUser();
    		Account acc = accountService.findByUsername(username);
    		User user = userService.findByUsername(username);
    		Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
    		
    		user.setAccount(acc);
    		user.setFullname(fullname);
    		user.setEmail(email);
    		user.setGender(gender);
    		user.setDob(newDate);
    		user.setPhone(phone);	
    		user.setPhoto(user.getPhoto());
    		user.setAddress(address);
    		
    		userService.create(user);
    		try {
    			uploadService.saveUser(photo,user);
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    		model.addAttribute("user", user);
    	
	

	return "redirect:/information";
	
    }
    
    @RequestMapping("/order/detail/{id}")
    public String viewCartDetail(Model model, @PathVariable("id") Long id) {   
    List<OrderCartViewDto> orderDetails = orderDetailService.findByIdOrderCartViewDto(id);
  	  model.addAttribute("orderDetails", orderDetails);
    	return "account/information";
    }
    

	/* ham gui ma xac thuc email */
    int checkPinNumber=0;
	Account getAccount;
    @RequestMapping("/verificationEmail")
    public String verificationEmail(@RequestParam("email") String email,HttpServletRequest request,Model model) {
    	String username = request.getRemoteUser();
    	getAccount = accountService.findByUsername(username);
		User u = userService.findByAccountUsername(username);
		try {
			if(email.equalsIgnoreCase(u.getEmail()) ) {
				String randomPIN ="";
				Random rdn = new Random();
				int number = rdn.nextInt(999999);
				randomPIN="" + number;
				if(number < 999999) {
					randomPIN = "0" + number;
				}
				checkPinNumber= Integer.parseInt(randomPIN);
				MailInfo m = new MailInfo();
				m.setFrom("testemailnghiatran@gmail.com");
				m.setSubject("Verification your email");
				m.setTo(email);
				m.setBody("OTP: "+checkPinNumber);
				try {
				    mailerServie.send(m);
				} catch (Exception e) {
				    System.out.println("Error : " + e.getMessage());
				}
				return "/account/checkPinEmail";
			}else {
				model.addAttribute("mSendMail","Email không tồn tại, hãy update email!");
				return "redirect:/information";
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("mSendMail","Lỗi");
			return "redirect:/information";
		}
    	
    
    }

	@RequestMapping("/checkPinEmail")
	public String checkPin(Model model, @RequestParam("number") String number) {
	
		try {
			int pin = Integer.parseInt(number);
			if(pin==checkPinNumber) {
				isVerificationEmail = true;				
			}else {
				model.addAttribute("mCheckPinEmail","invalid verification code");				
			}
			return "redirect:/information";
		} catch (Exception e) {
			model.addAttribute("mCheckPinEmail","invalid verification code");
			return "redirect:/information";
		}
	
	}
    
  @PostMapping("/changePassword")
  public String changePassword(HttpServletRequest request,Model model,@RequestParam("currentPassword") String currentPassword,@RequestParam("newPassword") String newPassword
		  ,@RequestParam("confirmPassword") String confirmPassword) {
	  String username = request.getRemoteUser();
	  try {
		  Account acc = accountService.findByUsername(username); 
		  System.out.println(acc == null ? "Null" : "Not null"); 
		if(isVerificationEmail == true && newPassword.equals(confirmPassword) && pe.matches(currentPassword, acc.getPassword())) {		
			acc.setPassword(pe.encode(newPassword)); 
			accountService.update(acc);		
		}
	} catch (Exception e) {
		// TODO: handle exception		
	}
	  return "redirect:/information";
  }
}
