package com.godEShop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.OrderCartViewDto;
import com.godEShop.Entity.OrderDetail;

@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
	   @Query("SELECT new com.godEShop.Dto.OrderCartViewDto"
			   + "(od.id,Min(pp.id),p.name,odd.price,odd.quantity,od.createDate,od.address,ods.name) "
			   + "from OrderDetail odd "
			   + "inner join odd.order od  "			
			   + "inner join od.orderStatus ods " 
			   + "inner join odd.product p "
			   + "inner join p.productPhotos pp where od.id=?1 "
			   + "group by  od.id,p.name,odd.price,odd.quantity,od.createDate,od.address,ods.name ")
		    List<OrderCartViewDto> findByIdOrderCartViewDto(Long id);
}
