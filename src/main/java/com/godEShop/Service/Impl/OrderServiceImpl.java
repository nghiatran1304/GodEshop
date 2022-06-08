package com.godEShop.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godEShop.Dao.OrderDAO;
import com.godEShop.Dao.OrderDetailDAO;
import com.godEShop.Dao.OrderStatusDAO;
import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dto.OrderInfoDto;
import com.godEShop.Dto.OrderListDto;
import com.godEShop.Entity.Order;
import com.godEShop.Entity.OrderDetail;
import com.godEShop.Entity.Product;
import com.godEShop.Service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    
    @Autowired
    OrderStatusDAO osDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;
    
    @Autowired
    ProductDAO productDAO;

    @Override
    public Order create(JsonNode orderData) {
	// TODO Auto-generated method stub
	ObjectMapper mapper = new ObjectMapper();
	Order order = mapper.convertValue(orderData, Order.class);
	orderDAO.save(order);
	TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
	};
	List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
		.peek(d -> d.setOrder(order)).collect(Collectors.toList());
	orderDetailDAO.saveAll(details);
	
	// kiểm tra -> giảm số lượng sản phẩm ở đây
	for(int i = 0; i < details.size(); i++) {
	    Product oldProduct = productDAO.getById(details.get(i).getProduct().getId());
	    oldProduct.setQuantity(oldProduct.getQuantity() - details.get(i).getQuantity());
	    productDAO.save(oldProduct);
	    System.out.println("---- UPDATE QUANTITY PRODUCT WHEN ORDER SUCCESS ----");
	    System.out.println(i + " : Product Id : " + details.get(i).getProduct().getId());
	    System.out.println(i + " : Quantity : " + details.get(i).getQuantity());
	    System.out.println("------END UPDATE QUANTITY PRODUCT WHEN ORDER SUCCESS------");
	}
	
	return order;
    }

    @Override
    public Order findById(Long id) {
	// TODO Auto-generated method stub
	return orderDAO.findById(id).get();
    }

    @Override
    public List<Order> findAll() {
	// TODO Auto-generated method stub
	return orderDAO.findAll();
    }

    @Override
    public List<OrderListDto> findByUsername1(String username) {
	// TODO Auto-generated method stub
	return orderDAO.findByUsername1(username);
    }

    @Override
    public List<Order> findAllOrderPending() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderPending();
    }

    @Override
    public List<Order> findAllOrderConfirmed() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderConfirmed();
    }

    @Override
    public List<Order> findAllOrderDelivery() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderDelivery();
    }

    @Override
    public List<Order> findAllOrderSuccess() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderSuccess();
    }

    @Override
    public List<Order> findAllOrderCancel() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderCancel();
    }

    @Override
    public List<Order> findAllOrders() {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrders();
    }

    @Override
    public List<OrderInfoDto> findAllOrderInfoDto(Long id) {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderInfoDto(id);
    }

    @Override
    public Order updateConfirm(Order o) {
	// TODO Auto-generated method stub
	Order oldOder = o;
	oldOder.setOrderStatus(osDAO.getById(2));
	return orderDAO.save(oldOder);
    }

    @Override
    public Order updateDelivery(Order o) {
	// TODO Auto-generated method stub
	Order oldOder = o;
	oldOder.setOrderStatus(osDAO.getById(3));
	return orderDAO.save(oldOder);
    }

    @Override
    public Order updateSuccess(Order o) {
	// TODO Auto-generated method stub
	Order oldOder = o;
	oldOder.setOrderStatus(osDAO.getById(4));
	return orderDAO.save(oldOder);
    }
}
