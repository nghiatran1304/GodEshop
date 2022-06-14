package com.godEShop.Service;

import java.util.List;

import com.godEShop.Dto.UserInfoDto;
import com.godEShop.Entity.User;

public interface UserService {

    public User findByUsername(String username);

    public List<User> findAll();

    public User create(User newUser);

    User findByAccountUsername(String username);
    
    List<UserInfoDto> lstUserInfoDto();

    public User update(User u);

    public User findById(Integer userId);

    List<UserInfoDto> lstFindUser(String kw);

}
