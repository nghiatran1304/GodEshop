package com.godEShop.Dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.godEShop.Dto.OrderInfoDto;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.account.username = ?1")
    List<Order> findByUsername(String username);

    @Query("SELECT new com.godEShop.Dto.OrderListDto" + "(o.id,o.createDate,o.address,s.name) "
	    + "from Order o inner join o.account a  " + "inner join o.orderStatus s where a.username=?1 order by o.createDate desc, o.id desc")
    List<OrderListDto> findByUsername1(String username);

    @Query("SELECT o FROM Order o WHERE o.orderStatus.id = 1 ORDER BY o.createDate DESC")
    List<Order> findAllOrderPending();

    @Query("SELECT o FROM Order o WHERE o.orderStatus.id = 2 ORDER BY o.createDate DESC")
    List<Order> findAllOrderConfirmed();

    @Query("SELECT o FROM Order o WHERE o.orderStatus.id = 3 ORDER BY o.createDate DESC")
    List<Order> findAllOrderDelivery();

    @Query("SELECT o FROM Order o WHERE o.orderStatus.id = 4 ORDER BY o.createDate DESC")
    List<Order> findAllOrderSuccess();

    @Query("SELECT o FROM Order o WHERE o.orderStatus.id = 5 ORDER BY o.createDate DESC")
    List<Order> findAllOrderCancel();

    @Query("SELECT o FROM Order o ORDER BY o.orderStatus.id ASC, o.createDate DESC")
    List<Order> findAllOrders();
    
    @Query("SELECT new com.godEShop.Dto.OrderInfoDto"
    	+ "(o.id, os.id, a.username, o.createDate, u.fullname, u.phone, u.email, om.name, o.address, o.note, MIN(pp.id), p.name, od.price, od.quantity) "
    	+ "FROM Order o "
    	+ "INNER JOIN o.orderDetails od "
    	+ "INNER JOIN od.product p "
    	+ "INNER JOIN p.productPhotos pp "
    	+ "INNER JOIN o.account a "
    	+ "INNER JOIN a.users u "
    	+ "INNER JOIN o.orderMethod om "
    	+ "INNER JOIN o.orderStatus os "
    	+ "WHERE o.id = ?1 "
    	+ "GROUP BY o.id, os.id, a.username, o.createDate, u.fullname, u.phone, u.email, om.name, o.address, o.note, p.name, od.price, od.quantity")
    List<OrderInfoDto> findAllOrderInfoDto(Long id);
    
    @Query(value = "select count(*) from Orders", nativeQuery=true)
    int getTotalOrder();
    
    @Query(value = "select count(*) from Orders where createDate between ?1 and ?2", nativeQuery=true)
    int getTotalOrderByTime(Date dateStart, Date dateEnd);
    
    @Query(value = "select count(*) from Orders where orderStatusId = 1", nativeQuery=true)
    int getTotalPendingOrder();
    
    @Query(value = "select count(*) from Orders where orderStatusId = 1 and createDate between ?1 and ?2", nativeQuery=true)
    int getTotalPendingOrderByTime(Date dateStart, Date dateEnd);
    
    @Query(value = "select count(*) from Orders where orderStatusId = 2", nativeQuery=true)
    int getTotalConfirmOrder();
    
    @Query(value = "select count(*) from Orders where orderStatusId = 2 and createDate between ?1 and ?2", nativeQuery=true)
    int getTotalConfirmOrderByTime(Date dateStart, Date dateEnd);
    
    @Query(value = "select count(*) from Orders where orderStatusId = 3", nativeQuery=true)
    int getTotalDeliveryOrder();
    
    @Query(value = "select count(*) from Orders where orderStatusId = 3 and createDate between ?1 and ?2", nativeQuery=true)
    int getTotalDeliveryOrderByTime(Date dateStart, Date dateEnd);
    
    @Query(value = "select count(*) from Orders where orderStatusId = 4", nativeQuery=true)
    int getTotalCompleteOrder();
    
    @Query(value = "select count(*) from Orders where orderStatusId = 4 and createDate between ?1 and ?2", nativeQuery=true)
    int getTotalCompleteOrderByTime(Date dateStart, Date dateEnd);
    
    @Query("SELECT o FROM Order o WHERE o.account.username LIKE ?1 ORDER BY o.createDate DESC")
    List<Order> findAllOrderBySearch(String kw);
    
    
    
}
