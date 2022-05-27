package com.godEShop.Service;

import java.util.List;


import com.godEShop.Entity.Account;


public interface AccountService {

	public Account findById(String username);

	public List<Account> findAll();

	public Account create(Account account);
	
//	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2);

}
