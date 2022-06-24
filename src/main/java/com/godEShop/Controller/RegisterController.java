package com.godEShop.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.godEShop.Entity.Account;
import com.godEShop.Entity.Role;
import com.godEShop.Entity.User;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.RoleService;
import com.godEShop.Service.SessionService;
import com.godEShop.Service.UserService;

@Controller
public class RegisterController {
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    SessionService sessionService;

    @Autowired
    BCryptPasswordEncoder pe;

    @PostMapping("/register")
    public String Register(Model model, @RequestParam("username") String username,
	    @RequestParam("password") String password, @RequestParam("txtPhone") String phoneNumber) {
	try {
		List<User> oldUser = userService.findAll();	
	//	System.out.println("phone user 39:"+oldUser.get(40).getPhone());
		for (User user : oldUser) {
			if(phoneNumber.equals(user.getPhone())) {
				model.addAttribute("mIsPhoneExisted", "true");
				model.addAttribute("messageRegister", "FAILED");
				return "forward:/account/login/form";
			}
		}
		
	    if (userService.findByUsername(username) == null && username.trim().length() > 0
		    && password.trim().length() > 0 && phoneNumber.trim().length() > 0) {
	    
		Account acc = new Account();
		acc.setUsername(username);
		acc.setPassword(pe.encode(password));
		acc.setIsDelete(false);

		Role role = roleService.findById("Customer");
		acc.setRole(role);
		accountService.create(acc);
		User newUser = new User();
		newUser.setAccount(acc);
		newUser.setFullname(acc.getUsername());
		newUser.setEmail("");
		newUser.setGender(1);
		newUser.setDob(new Date());
		newUser.setPhone(phoneNumber);
		newUser.setPhoto("");
		newUser.setAddress("");
		userService.create(newUser);
		model.addAttribute("messageRegister", "REGISTER SUCCESS");
	    } else {
		model.addAttribute("messageRegister", "ACCOUNT IS EXISTED");
		return "forward:/account/login/form";
	    }
	} catch (Exception e) {
	    // TODO: handle exception
		System.out.println(e);
	    model.addAttribute("messageRegister", "FAILED");
	    return "forward:/account/login/form";
	}
	return "forward:/account/login/form";
    }
}
