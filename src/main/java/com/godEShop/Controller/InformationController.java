package com.godEShop.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Dto.OrderCartViewDto;
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
	Boolean isVerificationEmail = false;
	
	static Boolean CheckUpdateChangeInfo = false;
	static String mChangePass="";
	static String mUpdate = "";
	@RequestMapping("/information")
	public String informationPage(HttpServletRequest request, Model model) {
		if(CheckUpdateChangeInfo) {
			 if(mUpdate.trim().length() <= 0 ) {
				 mUpdate = "";
			    	model.addAttribute("mUpdate",mUpdate);
			    }else {
			    	model.addAttribute("mUpdate",mUpdate);
			    }
		}
		
		 if(mChangePass.trim().length() <= 0 ) {
			 mChangePass = "";
		    	model.addAttribute("mChangePass",mChangePass);
		    }else {
		    	model.addAttribute("mChangePass",mChangePass);
		    }
		
		String username = request.getRemoteUser();
		User user = userService.findByUsername(username);

		if (user.getPhoto().contains("jpg") || user.getPhoto().contains("png") || user.getPhoto().contains("JPG")
				|| user.getPhoto().contains("PNG")) {
			model.addAttribute("isOAuth2", false);
		} else {
			model.addAttribute("isOAuth2", true);
		}
		if (isVerificationEmail == true) {
			model.addAttribute("isVerificationEmail", "true");
		} else if (isVerificationEmail == false) {
			model.addAttribute("isVerificationEmail", "false");
		}
		
		model.addAttribute("user", user);
		
		isVerificationEmail = false;
		
		if(CheckUpdateChangeInfo == false) {
			mUpdate = "";
		}
		mUpdate = "";
		
		return "account/information";
	}

	@PostMapping("/update/accountDetail")
	public String updateAccountDetail(Model model, HttpServletRequest request,
			@RequestParam("fullname") String fullname, @RequestParam("email") String email,
			@RequestParam("gender") Integer gender, @RequestParam("dob") String dob,
			@RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestPart("photo") MultipartFile photo) throws ParseException {
		try {

			String username = request.getRemoteUser();
			Account acc = accountService.findByUsername(username);
			User userUpdate = userService.findByUsername(username);
			Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
	
			User userUpdateEmail = userService.findByEmail(email);
			User userUpdatePhone = userService.findByPhone(phone);
			
				if (!userUpdate.getEmail().equals(email)) {
					CheckUpdateChangeInfo = true;
					if (userUpdateEmail == null) { 
						userUpdate.setAccount(acc);
						userUpdate.setFullname(fullname);
						userUpdate.setEmail(email);
						userUpdate.setGender(gender);
						userUpdate.setDob(newDate);
						userUpdate.setPhone(phone);
						userUpdate.setPhoto(userUpdate.getPhoto());
						userUpdate.setAddress(address);

						if (!photo.isEmpty()) {
							uploadService.saveUser(photo, userUpdate);
							mUpdate = "UPDATE SUCCESS";
						}
						userService.create(userUpdate);
						mUpdate = "UPDATE SUCCESS";
					} else {
						
						mUpdate = "YOUR EMAIL NUMBER IS USED";						
					}
				}else
			
				if (!userUpdate.getPhone().equals(phone)) {
					CheckUpdateChangeInfo = true;
					if (userUpdatePhone == null) { 
						userUpdate.setAccount(acc);
						userUpdate.setFullname(fullname);
						userUpdate.setEmail(email);
						userUpdate.setGender(gender);
						userUpdate.setDob(newDate);
						userUpdate.setPhone(phone);
						userUpdate.setPhoto(userUpdate.getPhoto());
						userUpdate.setAddress(address);

						if (!photo.isEmpty()) {
							uploadService.saveUser(photo, userUpdate);
							mUpdate = "UPDATE SUCCESS";
						}
						userService.create(userUpdate);
						mUpdate = "UPDATE SUCCESS";
						
					} else {										
						mUpdate = "YOUR PHONE NUMBER IS USED";
						}
				}else if(!userUpdate.getFullname().equals(fullname) 
					|| !userUpdate.getGender().equals(gender) || !userUpdate.getDob().equals(newDate)
					|| !userUpdate.getAddress().equals(address) || !userUpdate.getPhoto().equals(photo))	{
					CheckUpdateChangeInfo = true;
					userUpdate.setAccount(acc);
					userUpdate.setFullname(fullname);
					userUpdate.setEmail(email);
					userUpdate.setGender(gender);
					userUpdate.setDob(newDate);
					userUpdate.setPhone(phone);
					userUpdate.setPhoto(userUpdate.getPhoto());
					userUpdate.setAddress(address);

					if (!photo.isEmpty()) {
						uploadService.saveUser(photo, userUpdate);
						mUpdate = "UPDATE SUCCESS";
					}
					userService.create(userUpdate);
					mUpdate = "UPDATE SUCCESS";
				}else {
					CheckUpdateChangeInfo = false;
					mUpdate = "";
				}
	
			return "redirect:/information";

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error:" + e);			
			return "forward:/information";
		}

	}

	@RequestMapping("/order/detail/{id}")
	public String viewCartDetail(Model model, @PathVariable("id") Long id) {
		List<OrderCartViewDto> orderDetails = orderDetailService.findByIdOrderCartViewDto(id);
		model.addAttribute("orderDetails", orderDetails);
		return "account/information";
	}

	/* ham gui ma xac thuc email */
	int checkPinNumber = 0;
	Account getAccount;

	@RequestMapping("/verificationEmail")
	public String verificationEmail(@RequestParam("email") String email, HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		getAccount = accountService.findByUsername(username);
		User u = userService.findByAccountUsername(username);

		try {
			if (email.equalsIgnoreCase(u.getEmail())) {

				int number = (int) Math.floor(((Math.random() * 899999) + 100000));
				checkPinNumber = number;
				MailInfo m = new MailInfo();
				m.setFrom("testemailnghiatran@gmail.com");
				m.setSubject("Verification your email");
				m.setTo(email);
				m.setBody("OTP: " + checkPinNumber);

				try {
					mailerServie.send(m);
					isVerificationEmail = false;
				} catch (Exception e) {
					isVerificationEmail = false;
					System.out.println("Error : " + e.getMessage());
				}

				return "/account/checkPinEmail";
			} else {
				isVerificationEmail = false;
				model.addAttribute("mSendMail", "Email does not exist!");
				return "forward:/information";
			}
		} catch (Exception e) {
			// TODO: handle exception
			isVerificationEmail = false;
			model.addAttribute("mSendMail", "Lỗi");
			return "redirect:/information";
		}
	}

	@RequestMapping("/checkPinEmail")
	public String checkPin(Model model, @RequestParam("number") String number) {

		try {
			int pin = Integer.parseInt(number);
			if (pin == checkPinNumber) {
				isVerificationEmail = true;
			} else {
				isVerificationEmail = false;
				model.addAttribute("mCheckPinEmail", "invalid verification code");
			}
			return "redirect:/information";

		} catch (Exception e) {
			isVerificationEmail = false;
			model.addAttribute("mCheckPinEmail", "invalid verification code");
			return "account/checkPinEmail";
		}

	}

	@PostMapping("/changePassword")
	public String changePassword(HttpServletRequest request, Model model,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		try {
			String username = request.getRemoteUser();
			Account acc = accountService.findByUsername(username);

			if (!newPassword.equals(confirmPassword)) {
				mChangePass = "confirmPassword and newPassword does not match";
				
				isVerificationEmail = true;
				return "forward:/information";
			} else if (!pe.matches(currentPassword, acc.getPassword())) {
				mChangePass = "CurrentPassword incorrect" ; 
				
				isVerificationEmail = true;
				return "forward:/information";
			} else {				
				acc.setPassword(pe.encode(newPassword));
				accountService.update(acc);	
				mChangePass = "CHANGE PASSWORD SUCCESS";
				isVerificationEmail = false;					
				return "redirect:/account/logoff";				
			}
			 	
		} catch (Exception e) {
			// TODO: handle exception
			isVerificationEmail = true;
			mChangePass = "CHANGE PASSWORD FAIL" ;			
			return "forward:/information";
		}

	}
}
