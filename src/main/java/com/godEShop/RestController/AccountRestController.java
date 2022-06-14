package com.godEShop.RestController;

import java.io.File;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.godEShop.Dto.UserInfoDto;
import com.godEShop.Entity.Account;
import com.godEShop.Entity.Role;
import com.godEShop.Entity.User;
import com.godEShop.Service.AccountService;
import com.godEShop.Service.FileManagerService;
import com.godEShop.Service.RoleService;
import com.godEShop.Service.UploadService;
import com.godEShop.Service.UserService;

@CrossOrigin("*")
@RestController
public class AccountRestController {
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    
    @Autowired
    UploadService uploadService;
    
    @Autowired
    FileManagerService fileService;

    @GetMapping("/rest/accounts")
    public List<Account> getAll() {
	return accountService.findAll();
    }

    @GetMapping("/rest/info-accounts")
    public List<UserInfoDto> getInfo() {
	return userService.lstUserInfoDto();
    }

    @DeleteMapping("/rest/delete-account/{accountUsername}")
    public void deleteUsername(@PathVariable("accountUsername") String id) {
	accountService.delete(id);
    }

    @PutMapping("/rest/update-account/{accountUsername}")
    public UserInfoDto update(@PathVariable("accountUsername") String id, @RequestBody UserInfoDto ui) {
	Role r = new Role();
	r = roleService.findById(ui.getRoleId());

	Account a = new Account();
	a.setUsername(ui.getAccountUsername());
	a.setPassword(ui.getAccountPassword());
	a.setIsDelete(ui.getAccountIsDeleted());
	a.setRole(r);
	accountService.update(a);

	User u = new User();
	u = userService.findById(ui.getUserId());

	u.setId(ui.getUserId());
	u.setAddress(ui.getUserAddress());
	u.setDob(ui.getUserDob());
	u.setEmail(ui.getUserEmail());
	u.setFullname(ui.getUserFullname());
	u.setGender(ui.getUserGender());
	u.setPhone(ui.getUserPhone());
	u.setPhoto(ui.getUserPhoto());
	u.setAccount(a);
	userService.update(u);

	return ui;
    }

    @PutMapping("/rest/photo-user")
    public UserInfoDto updatePhoto(@RequestBody UserInfoDto ui) {
	User u = new User();
	u = userService.findById(ui.getUserId());
	u.setPhoto(ui.getUserPhoto());
	userService.update(u);
	return ui;
    }
    
    @PostMapping("/rest/create-account")
    public UserInfoDto createUser(@RequestBody UserInfoDto ui) {
	Role r = new Role();
	r = roleService.findById(ui.getRoleId());
	Account a = new Account();
	a.setUsername(ui.getAccountUsername());
	a.setPassword(ui.getAccountPassword());
	a.setIsDelete(ui.getAccountIsDeleted());
	a.setRole(r);
	accountService.create(a);
	
	User u = new User();
	u.setAddress(ui.getUserAddress());
	u.setDob(ui.getUserDob());
	u.setEmail(ui.getUserEmail());
	u.setFullname(ui.getUserFullname());
	u.setGender(ui.getUserGender());
	u.setPhone(ui.getUserPhone());
	u.setPhoto(ui.getUserPhoto());
	u.setAccount(a);
	userService.create(u);
	
	return ui;
    }
    
    
    // ----- IMAGE
    
    @PostMapping("/rest/upload-user/{folder}")
    public JsonNode uploadAdmin(@PathParam("file") MultipartFile file, @PathVariable("folder") String folder) {
	File savedFile = uploadService.saveUserAdmin(file, folder);
	ObjectMapper mapper = new ObjectMapper();
	ObjectNode node = mapper.createObjectNode();
	node.put("name", savedFile.getName());
	node.put("size", savedFile.length());
	return node;
    }
    
    // --------------------------- UPLOAD USER PHOTO
    @PutMapping("/rest/photo-user/{userId}")
    public List<String> uploadImagesUser(@PathVariable("userId") Integer id, @PathParam("files") MultipartFile[] files) {
	return fileService.saveAdmin(id, files);
    } 

}
