package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.UserInfoDto;
import com.godEShop.Entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM Users WHERE username LIKE ?1", nativeQuery = true)
    User findByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.account.username=?1")
    User findByAccountUsername(String username);
    
    @Query("SELECT new com.godEShop.Dto.UserInfoDto"
    	+ "(a.username, a.isDelete, a.password, r.id, r.name, u.id, u.address, u.dob, u.email, u.fullname, u.gender, u.phone, u.photo)"
    	+ "FROM Account a "
    	+ "INNER JOIN a.role r "
    	+ "INNER JOIN a.users u ")
    List<UserInfoDto> lstUserInfoDto();
    
    @Query("SELECT new com.godEShop.Dto.UserInfoDto"
	+ "(a.username, a.isDelete, a.password, r.id, r.name, u.id, u.address, u.dob, u.email, u.fullname, u.gender, u.phone, u.photo)"
	+ "FROM Account a "
	+ "INNER JOIN a.role r "
	+ "INNER JOIN a.users u "
	+ "WHERE a.username LIKE ?1 OR u.fullname LIKE ?1")
    List<UserInfoDto> lstFindUser(String kw);
}
