package com.godEShop.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Account;
import com.godEShop.Entity.User;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.OrderService;
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

    @GetMapping("/information")
    public String informationPage(HttpServletRequest request, Model model) {
	String username = request.getRemoteUser();
	User user = userService.findByUsername(username);

	if (user.getPhoto().contains("jpg")) {
	    model.addAttribute("isOAuth2", false);
	} else {
	    model.addAttribute("isOAuth2", true);
	}
	model.addAttribute("user", user);

	List<OrderListDto> orders = orderService.findByUsername1(username);
	if (orders.size() != 0) {
	    model.addAttribute("orders", orders);
	} else {
	    model.addAttribute("message", "is not have any orders before here");
	}

	return "account/information";
    }

    @PostMapping("/update/address")
    public String updateAddress(Model model, @RequestParam("address") String address,
	    @RequestParam("phone") String phone, HttpServletRequest request) {
	String username = request.getRemoteUser();
	Account acc = accountService.findById(username);
	User user = userService.findByUsername(username);

	user.setAccount(acc);
	user.setFullname(user.getFullname());
	user.setEmail(user.getEmail());
	user.setGender(user.getGender());
	user.setDob(user.getDob());
	user.setPhone(phone);
	user.setPhoto(user.getPhoto());
	user.setAddress(address);

	userService.create(user);
	model.addAttribute("user", user);
	return "account/information";
    }

    @PostMapping("/update/accountDetail")
    public String updateAccountDetail(Model model, HttpServletRequest request,
	    @RequestParam("fullname") String fullname, @RequestParam("email") String email,
	    @RequestParam("gender") Integer gender, @RequestParam("dob") String dob,
	    @RequestParam("photo") MultipartFile photo) throws ParseException {

	String username = request.getRemoteUser();
	Account acc = accountService.findById(username);
	User user = userService.findByUsername(username);
	Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);

	try {
//					if(!photo.isEmpty()) {
//						String fileName= photo.getOriginalFilename();
//						File file = new File(app.getRealPath("/")+"static/assets/images/users");
//						photo.transferTo(file);
//					}

	    Path path = Paths.get(app.getRealPath("/") + "src/main/resources/static/upload/UserImages");
	    System.out.println(path);
	    InputStream ips = photo.getInputStream();
	    Files.copy(ips, path.resolve(photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

	} catch (Exception e) {
	    // TODO: handle exception
	    System.out.println(e.getMessage());
	}
	user.setAccount(acc);
	user.setFullname(fullname);
	user.setEmail(email);
	user.setGender(gender);
	user.setDob(newDate);
	user.setPhone(user.getPhone());
	user.setPhoto(photo.getOriginalFilename());
	user.setAddress(user.getAddress());

	userService.create(user);
	model.addAttribute("user", user);

	return "redirect:/information";
    }

}
