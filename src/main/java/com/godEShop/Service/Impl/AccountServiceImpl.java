package com.godEShop.Service.Impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.godEShop.Dao.AccountDAO;
import com.godEShop.Entity.Account;
import com.godEShop.Service.AccountService;
@Service
public class AccountServiceImpl implements AccountService{
@Autowired
AccountDAO adao;

@Override
public Account findById(String username) {
	// TODO Auto-generated method stub
	return adao.findById(username).get();
}

//@Override
//public List<Account> getAdministrations() {
//	// TODO Auto-generated method stub
//	return adao.getAdministrations();
//}

@Override
public List<Account> findAll() {
	// TODO Auto-generated method stub
	return adao.findAll();
}
//@Autowired 
//BCryptPasswordEncoder pe;


//@Override
//public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
//
////		String username = oauth2.getPrincipal().getAttribute("username");
//		String email = oauth2.getPrincipal().getAttribute("email");
//		//sinh mật khẩu ngẫu nhiên
//		String password = Long.toHexString(System.currentTimeMillis());
//		//Tạo UserDetails từ Account 
//		UserDetails user = User.withUsername(email)
//				.password(pe.encode(password)).roles("GUEST").build();
//	
//	Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
//	// thay thế authentication này bằng authentication từ mạng xh
//	SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContextHolder: nơi chứa tt secutiry
//		
//	
//	
//}
// lưu tài khoản oauth2 vào database
//@Override
//public Account create( Account account) {
//	// TODO Auto-generated method stub
//	return adao.save(account);
//}
//
//@Override
//public Account update(Account account) {
//	// TODO Auto-generated method stub
//	return adao.save(account);
//}
//
//@Override
//public void delete(String username) {
//	// TODO Auto-generated method stub
//	adao.deleteById(username);
//}


}

