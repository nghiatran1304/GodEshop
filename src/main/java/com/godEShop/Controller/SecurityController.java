package com.godEShop.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.godEShop.Dao.AccountDAO;



@Controller
public class SecurityController {
//	@Autowired 
//	BCryptPasswordEncoder pe;
	@Autowired
	HttpServletRequest request;
	AccountDAO adao ; 
	
//	List<Account> lstAccount = accountService.findAll();
	
	@RequestMapping("/account/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "account/login";	
	}
	@RequestMapping("/account/login/success")
	public String loginSuccess(Model model,HttpServletRequest req) {
		model.addAttribute("message", "Đăng nhập thành công!");
		if(req.isUserInRole("Admin")) {
			return "redirect:/index-admin";
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

//	@RequestMapping("/oauth2/login/success")
//	public String sucess(OAuth2AuthenticationToken oauth2){ // toàn bộ thông tin đăng nhập từ mạng xh nằm trong đối tượng này
////		accountService.create(oauth2); //
//		// Đọc thông tin tài khoản từ mạng xã hội
//		
//		String email = oauth2.getPrincipal().getAttribute("email");
//		String fullname = oauth2.getPrincipal().getAttribute("name");
//		String photo = oauth2.getPrincipal().getAttribute("picture");
//		 String password = Long.toHexString(System.currentTimeMillis());
//	//	 Account accountCSDL = accountService.findById(email);
////		 for (Account acc : lstAccount) {
//	//		 if(!accountCSDL.getEmail().equals(email)) {
//					Account account = new Account();
//					account.setPassword(password);
//					account.setUsername(email);
//					account.setFullname(fullname);
//					account.setEmail(email);	
//					account.setPhoto(photo);
//				
//					System.out.println(account);
//					accountService.create(account);
//	//		 }else {
//	//		 System.out.println("Có rồi");
//	//	 }
////			
////		}
//	
//		
//		accountService.loginFromOAuth2(oauth2);
//	  return "forward:/account/login/success";
//	}
}

