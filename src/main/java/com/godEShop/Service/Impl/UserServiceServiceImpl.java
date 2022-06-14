package com.godEShop.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.UserDAO;
import com.godEShop.Dto.UserInfoDto;
import com.godEShop.Entity.User;
import com.godEShop.Service.UserService;

@Service
public class UserServiceServiceImpl implements UserService {
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

    @Override
    public User create(User newUser) {
	// TODO Auto-generated method stub
	return udao.save(newUser);
    }

    @Override
    public User findByAccountUsername(String username) {
	// TODO Auto-generated method stub
	return udao.findByAccountUsername(username);
    }

    @Override
    public List<UserInfoDto> lstUserInfoDto() {
	// TODO Auto-generated method stub
	return udao.lstUserInfoDto();
    }

    @Override
    public User update(User u) {
	// TODO Auto-generated method stub
	return udao.save(u);
    }

    @Override
    public User findById(Integer userId) {
	// TODO Auto-generated method stub
	return udao.getById(userId);
    }

    @Override
    public List<UserInfoDto> lstFindUser(String kw) {
	// TODO Auto-generated method stub
	return udao.lstFindUser(kw);
    }

}
