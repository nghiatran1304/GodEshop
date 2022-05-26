package com.godEShop.Service;

import java.util.List;

//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.godEShop.Entity.Account;


public interface AccountService {

	public Account findById(String username);

	public List<Account> findAll();
	
//	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2);

}
