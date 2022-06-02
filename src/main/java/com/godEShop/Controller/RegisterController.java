package com.godEShop.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/register")
	public String registerPage(Model model) {
		if (sessionService.get("messageRegister") != null) {
			model.addAttribute("messageRegister", sessionService.get("messageRegister"));
		}
		return "account/register";
	}

	@PostMapping("/register")
	public String Register(Model model, @RequestParam("username") String username,
			@RequestParam("password") String password) {
		if (username.length() > 2 && password.length() > 2) {
			if (userService.findByUsername(username) == null) {
				Account acc = new Account();
				acc.setUsername(username);
				acc.setPassword(password);
				acc.setIsDelete(false);

				Role role = roleService.findById("Customer");
				acc.setRole(role);
				accountService.create(acc);
				User newUser = new User();
				newUser.setAccount(acc);
				newUser.setFullname("");
				newUser.setEmail("");
				newUser.setGender(1);
				newUser.setDob(new Date());
				newUser.setPhone("");
				newUser.setPhoto("");
				newUser.setAddress("");
				userService.create(newUser);
				try {
					return "redirect:/account/login/form";
				} catch (Exception e) {
					// TODO: handle exception
					sessionService.set("messageRegister", "FAILED");
					return "redirect:/register";
				}
			} else {
				sessionService.set("messageRegister", "ACCOUNT IS EXISTED");
				return "redirect:/register";
			}
		} else {
			sessionService.set("messageRegister", "FAILED");
			return "redirect:/register";
		}

	}
}
