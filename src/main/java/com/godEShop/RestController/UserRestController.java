package com.godEShop.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.User;
import com.godEShop.Service.UserService;

@CrossOrigin("*")
@RestController
public class UserRestController {
    
    @Autowired
    UserService userService;
    
    @GetMapping("/rest/getUserInfomation/{us}")
    public User getUser(@PathVariable("us") String us) {
	return userService.findByAccountUsername(us);
    }
    
}
