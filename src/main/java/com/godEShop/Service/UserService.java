package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.User;




public interface UserService {

	public User findByUsername(String username);

	public List<User> findAll();
	


}
