package com.godEShop.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.godEShop.Entity.Account;
import com.godEShop.Entity.Role;
import com.godEShop.Entity.User;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.RoleService;
import com.godEShop.Service.SessionService;
import com.godEShop.Service.UserService;
import com.godEShop.Service.Impl.UserDetailImpl;

@Controller
public class SecurityController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    UserDetailImpl userDetail;

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    SessionService sessionService;

    @Autowired
    ServletContext servletContext;

    List<String> usernameLoginFail = new ArrayList<>();
    String temp = "";

    @RequestMapping({ "/account/login/form", "/loginPage" })
    public String loginForm(Model model) {
	model.addAttribute("message", "PLEASE LOG IN");
	return "account/login";
    }

    @RequestMapping("/admin")
    public String adminPage() {
	sessionService.set("adminName", userService.findByAccountUsername(req.getRemoteUser()));
	return "../static/admin/index.html";
    }

    @RequestMapping("/account/login/success")
    public String loginSuccess(Model model, HttpServletRequest request, HttpServletResponse response) {
	sessionService.remove("getUsernameValue");
	temp = "";
	usernameLoginFail = new ArrayList<>();
	model.addAttribute("messageLogin", "LOGIN SUCCESS");
	if (req.isUserInRole("Admin")) {
	    return "redirect:/admin";
	}
	return "redirect:/index";
    }

    @RequestMapping("/account/login/error")
    public String loginError(Model model) {
	model.addAttribute("messageLogin", "WRONG LOGIN INFORMATION!");
	// System.out.println(sessionService.get("getUsernameValue").toString());
	int count = 0;
	usernameLoginFail.add(sessionService.get("getUsernameValue").toString());
	String usernameUsingLogin = sessionService.get("getUsernameValue").toString();
	for (int i = 0; i < usernameLoginFail.size(); i++) {
	    if (usernameUsingLogin.equalsIgnoreCase(usernameLoginFail.get(i))) {
		count++;
		if (count >= 3) {
		    temp = usernameUsingLogin;
		    break;
		}
	    }
	}
	if (count >= 3) {
	    return "redirect:/forgotPassword";
	}
	return "account/login";
    }

    @RequestMapping("/account/unauthoried")
    public String unauthoried(Model model) {
	model.addAttribute("message", "No access permission!");
	return "account/login";
    }

    @RequestMapping("/account/logoff/success")
    public String logoffSuccess(Model model) {
	sessionService.remove("adminName");
	// model.addAttribute("messageLogoff", "YOU ARE LOGGED OUT");
	return "account/login";
    }

    @RequestMapping("/oauth2/login/success")
    public String sucess(OAuth2AuthenticationToken oauth2) {
	// Đọc thông tin tài khoản từ mạng xã hội
	String email = oauth2.getPrincipal().getAttribute("email");
	String fullname = oauth2.getPrincipal().getAttribute("name");
	String photo = oauth2.getPrincipal().getAttribute("picture");
	String password = Long.toHexString(System.currentTimeMillis());

	System.out.println(oauth2.getPrincipal().getAttributes());

	if (userService.findByUsername(email) == null) {
	    Account newAccount = new Account();
	    newAccount.setUsername(email);
	    newAccount.setPassword(password);
	    newAccount.setIsDelete(false);

	    Role role = roleService.findById("Customer");
	    newAccount.setRole(role);
	    accountService.create(newAccount);
	    User newUser = new User();
	    newUser.setAccount(newAccount);
	    newUser.setFullname(fullname);
	    newUser.setEmail(email);
	    newUser.setGender(1);
	    newUser.setDob(new Date());
	    newUser.setPhone("");
	    newUser.setPhoto(photo.substring(8));
	    newUser.setAddress("");
	    userService.create(newUser);
	}

	userDetail.loginFromOAuth2(oauth2);
	return "forward:/account/login/success";
    }
}
