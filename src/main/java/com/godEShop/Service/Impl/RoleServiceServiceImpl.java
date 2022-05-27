package com.godEShop.Service.Impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godEShop.Dao.RoleDAO;
import com.godEShop.Entity.Role;
import com.godEShop.Service.RoleService;
@Service
public class RoleServiceServiceImpl implements RoleService{
@Autowired
RoleDAO rdao;

@Override
public Role findById(String id) {
	// TODO Auto-generated method stub
	return rdao.findById(id).get();
}








}

