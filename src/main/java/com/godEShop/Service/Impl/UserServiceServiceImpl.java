package com.godEShop.Service.Impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.UserDAO;
import com.godEShop.Entity.User;
import com.godEShop.Service.UserService;
@Service
public class UserServiceServiceImpl implements UserService{
@Autowired
UserDAO udao;



@Override
public List<User> findAll() {
	// TODO Auto-generated method stub
	return udao.findAll();
}

@Override
public User findByUsername(String username) {
	// TODO Auto-generated method stub
	return udao.findByUsername(username);
}



}

