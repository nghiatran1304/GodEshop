package com.godEShop.Controller;

import java.util.Date;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> 491b016e1deb779588efa57dcf3471d700f029d2

import javax.servlet.http.HttpServletRequest;

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
import com.godEShop.Service.UserService;
import com.godEShop.Service.Impl.UserDetailImpl;
<<<<<<< HEAD
=======


>>>>>>> 491b016e1deb779588efa57dcf3471d700f029d2

@Controller
public class SecurityController {
//	@Autowired 
//	BCryptPasswordEncoder pe;
<<<<<<< HEAD
    @Autowired
    HttpServletRequest request;

    @Autowired
    UserDetailImpl userDetail;
    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/account/login/form")
    public String loginForm(Model model) {
	model.addAttribute("message", "Vui lòng đăng nhập");
	return "account/login";
    }

    @RequestMapping("/account/login/success")
    public String loginSuccess(Model model, HttpServletRequest req) {
	model.addAttribute("message", "Đăng nhập thành công!");
	if (req.isUserInRole("Admin")) {
	    return "redirect:/index-admin";
=======
	@Autowired
	HttpServletRequest request;

	@Autowired
	UserDetailImpl userDetail;
	@Autowired
	AccountService accountService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@RequestMapping("/account/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "account/login";	
>>>>>>> 491b016e1deb779588efa57dcf3471d700f029d2
	}
	return "redirect:/index";
    }

    @RequestMapping("/account/login/error")
    public String loginError(Model model) {
	model.addAttribute("message", "Sai thông tin đăng nhập!");
	return "account/login";
    }

    @RequestMapping("/account/unauthoried")
    public String unauthoried(Model model) {
	model.addAttribute("message", "Không có quyền truy xuất!");
	return "account/login";
    }

    @RequestMapping("/account/logoff/success")
    public String logoffSuccess(Model model) {
	model.addAttribute("message", "Bạn đã đăng xuất!");
	return "account/login";
    }

    @RequestMapping("/oauth2/login/success")
    public String sucess(OAuth2AuthenticationToken oauth2) { // toàn bộ thông tin đăng nhập từ mạng xh nằm trong đối
							     // tượng này

	// Đọc thông tin tài khoản từ mạng xã hội

	String email = oauth2.getPrincipal().getAttribute("email");
	String fullname = oauth2.getPrincipal().getAttribute("name");
	String photo = oauth2.getPrincipal().getAttribute("picture");
	String password = Long.toHexString(System.currentTimeMillis());

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
	    newUser.setPhoto(photo);
	    newUser.setAddress("");
	    userService.create(newUser);
	}

<<<<<<< HEAD
	userDetail.loginFromOAuth2(oauth2);
	return "forward:/account/login/success";
    }
=======
	@RequestMapping("/oauth2/login/success")
	public String sucess(OAuth2AuthenticationToken oauth2){ // toàn bộ thông tin đăng nhập từ mạng xh nằm trong đối tượng này

		// Đọc thông tin tài khoản từ mạng xã hội
	
		String email = oauth2.getPrincipal().getAttribute("email");
		String fullname = oauth2.getPrincipal().getAttribute("name");
		String photo = oauth2.getPrincipal().getAttribute("picture");
		 String password = Long.toHexString(System.currentTimeMillis());
		List<User> lstUser = userService.findAll();
		
		
			if(userService.findByUsername(email) == null) {
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
				newUser.setPhoto(photo);
				newUser.setAddress("");	
				userService.create(newUser);
			}
				
					
		userDetail.loginFromOAuth2(oauth2);
	  return "forward:/account/login/success";
	}
>>>>>>> 491b016e1deb779588efa57dcf3471d700f029d2
}