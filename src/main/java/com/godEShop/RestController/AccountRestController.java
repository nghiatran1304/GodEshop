package com.godEShop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.godEShop.Entity.Account;
import com.godEShop.Service.AccountService;

@CrossOrigin("*")
@RestController
public class AccountRestController {
	@Autowired
	AccountService accountService;

	@GetMapping("/rest/accounts")
	public List<Account> getAll() {
		return accountService.findAll();
	}
	

}
