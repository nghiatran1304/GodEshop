package com.godEShop.Service;

import java.util.List;

import com.godEShop.Entity.Role;

public interface RoleService {
	public Role findById(String id);
	

	public List<Role> findAll();
}
