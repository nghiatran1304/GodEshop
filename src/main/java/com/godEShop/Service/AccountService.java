package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.Account;

public interface AccountService {

    // lấy account với điều kiện isDelete=0
    public Account findByUsername(String username);

    public Account getById(String username);

    public List<Account> findAll();

    public Account create(Account account);

    public Account update(Account account);

    public void delete(String id);

//	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2);

}
