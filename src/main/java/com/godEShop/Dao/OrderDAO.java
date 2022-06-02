package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long>{
	@Query("SELECT new com.godEShop.Dto.OrderListDto"
			+ "(o.id,o.createDate,o.address,s.name) "
			+ "from Order o inner join o.account a  "
			+ "inner join o.orderStatus s where a.username=?1")
	List<OrderListDto> findByUsername(String username);
}
