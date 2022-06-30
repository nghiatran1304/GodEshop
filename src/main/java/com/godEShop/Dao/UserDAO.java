package com.godEShop.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.UserInfoDto;
import com.godEShop.Dto.UserOrderedStatisticDto;
import com.godEShop.Dto.UserStatisticDto;
import com.godEShop.Dto.UsersStatisticDto;
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
    
    @Query("SELECT new com.godEShop.Dto.UsersStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, count(o.id), sum(od.quantity*od.price)) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and o.orderStatus between 2 and 4 "
    		+ "GROUP BY u.id, a.username, u.fullname, u.photo "
    		+ "ORDER BY sum(od.quantity*od.price) DESC")
    List<UsersStatisticDto> getAllUserStat();
    
    @Query("SELECT new com.godEShop.Dto.UsersStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, count(o.id), sum(od.quantity*od.price)) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and o.orderStatus between 2 and 4 and o.createDate between ?1 and ?2 "
    		+ "GROUP BY u.id, a.username, u.fullname, u.photo "
    		+ "ORDER BY sum(od.quantity*od.price) DESC")
    List<UsersStatisticDto> getAllUserStatByTime(Date dateStart, Date dateEnd);
    
    @Query("SELECT new com.godEShop.Dto.UsersStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, count(o.id), sum(od.quantity*od.price)) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and o.orderStatus between 2 and 4 and a.username like ?1 "
    		+ "GROUP BY u.id, a.username, u.fullname, u.photo")
    List<UsersStatisticDto> find1UserStat(String username);
    
    @Query("SELECT new com.godEShop.Dto.UsersStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, count(o.id), sum(od.quantity*od.price)) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and o.orderStatus between 2 and 4 and a.username like ?1 and o.createDate between ?2 and ?3 "
    		+ "GROUP BY u.id, a.username, u.fullname, u.photo")
    List<UsersStatisticDto> find1UserStatByTime(String username, Date dateStart, Date dateEnd);

    @Query("SELECT new com.godEShop.Dto.UserStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, o.createDate, od.price, od.quantity, o.orderStatus) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE u.id = ?1 "
    		+ "ORDER BY o.createDate")
    List<UserStatisticDto> get1UserStat(int id);
    
    @Query("SELECT new com.godEShop.Dto.UserStatisticDto"
    		+ "(u.id, a.username, u.fullname, u.photo, o.createDate, od.price, od.quantity, o.orderStatus) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE u.id = ?1 and o.createDate between ?2 and ?3 "
    		+ "ORDER BY o.createDate")
    List<UserStatisticDto> get1UserStatByTime(int id, Date dateStart, Date dateEnd);
    
    @Query(value = "SELECT COUNT(*) FROM Users u inner join Accounts a on a.username = u.username where roleid != 'Admin'", nativeQuery = true)
    int getTotalAccount();
    
    @Query(value = "SELECT COUNT(*) FROM Users u inner join Accounts a on a.username = u.username where roleid != 'Admin' and gender = 1", nativeQuery = true)
    int getTotalAccountByMale();
    
    @Query("SELECT new com.godEShop.Dto.UserOrderedStatisticDto"
    		+ "(u.id, a.username) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and a.username in (select account from Order) "
    		+ "ORDER BY u.id, a.username")
    List<UserOrderedStatisticDto> getTotalAccountOrdered();
    
    @Query("SELECT new com.godEShop.Dto.UserOrderedStatisticDto"
    		+ "(u.id, a.username) "
    		+ "FROM Order o "
    		+ "INNER JOIN o.account a "
    		+ "INNER JOIN a.users u "
    		+ "INNER JOIN o.orderDetails od "
    		+ "WHERE a.role != 'Admin' and a.username in (select account from Order) and o.createDate between ?1 and ?2 "
    		+ "ORDER BY u.id, a.username")
    List<UserOrderedStatisticDto> getTotalAccountOrderedByTime(Date dateStart, Date dateEnd);
}
