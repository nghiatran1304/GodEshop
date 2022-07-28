package com.godEShop.Service.Impl;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godEShop.Dao.OrderDAO;
import com.godEShop.Dao.OrderDetailDAO;
import com.godEShop.Dao.OrderMethodDAO;
import com.godEShop.Dao.OrderStatusDAO;
import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dao.UserDAO;
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
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    OrderMethodDAO orderMethodDAO;

    @Override
    public Order create(JsonNode orderData) {
	// TODO Auto-generated method stub

	ObjectMapper mapper = new ObjectMapper();

	boolean flag = false;
	TypeReference<List<OrderDetail>> typeTest = new TypeReference<List<OrderDetail>>() {
	};
	List<OrderDetail> detailsTest = mapper.convertValue(orderData.get("orderDetails"), typeTest).stream()
		.collect(Collectors.toList());
	for (int i = 0; i < detailsTest.size(); i++) {
	    Product oldProduct = productDAO.getById(detailsTest.get(i).getProduct().getId());
	    if (oldProduct.getQuantity() <= 0) {
		flag = true;
		break;
	    }
	}
	if (flag) {
	    return null;
	}

	Order order = mapper.convertValue(orderData, Order.class);
	Order afterSave = orderDAO.save(order);
	TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
	};
	List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"), type).stream()
		.peek(d -> d.setOrder(order)).collect(Collectors.toList());
	orderDetailDAO.saveAll(details);
	// kiểm tra -> giảm số lượng sản phẩm ở đây
	for (int i = 0; i < details.size(); i++) {
	    Product oldProduct = productDAO.getById(details.get(i).getProduct().getId());
	    oldProduct.setQuantity(oldProduct.getQuantity() - details.get(i).getQuantity());
	    productDAO.save(oldProduct);
	    System.out.println("---- UPDATE QUANTITY PRODUCT WHEN ORDER SUCCESS ----");
	    System.out.println(i + " : Product Id : " + details.get(i).getProduct().getId());
	    System.out.println(i + " : Quantity : " + details.get(i).getQuantity());
	    System.out.println("------END UPDATE QUANTITY PRODUCT WHEN ORDER SUCCESS------");
	}

	// gui mail hoa don
	double getBillTotal = 0;
	StringBuilder renderListProduct = new StringBuilder();
	for(int i = 0; i < details.size(); i++) {
	    Product oldProduct = productDAO.getById(details.get(i).getProduct().getId());
	    getBillTotal += (details.get(i).getPrice() * details.get(i).getQuantity());
	    renderListProduct.append("<tr>\r\n"
	    	+ "<td>" + oldProduct.getName() + "</td>\r\n"
	    	+ "<td>" + details.get(i).getPrice() + " $</td>\r\n"
	    	+ "<td>" + details.get(i).getQuantity() + "</td>\r\n"
	    	+ "<td>" + String.valueOf(String.format("%.2f", (details.get(i).getPrice() * details.get(i).getQuantity())).toString())+ " $</td>\r\n"
	    	+ "</tr>\r\n");
	}
	String totalBill = String.valueOf(String.format("%.2f", getBillTotal).toString());
	String userMail = userDAO.findByAccountUsername(order.getAccount().getUsername()).getEmail();
	
	Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.auth", "true");
	
	String username = "testemailnghiatran@gmail.com";
	String password = "csxbhqamnaryttiv";
	Session session = Session.getInstance(props, new Authenticator() {
		@Override 
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	});
	StringBuilder strB = new StringBuilder();
	String emailTo = userMail;
	String emailSubject = "PURCHASE INVOICE";
	// String emailContent = "";
	strB.append("<!DOCTYPE html>\r\n"
		+ "<html lang='en' xmlns:th=\"http://www.thymeleaf.org\">\r\n"
		+ "<head>\r\n"
		+ "    <meta charset='UTF-8'>\r\n"
		+ "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\r\n"
		+ "    <title>Fabcart</title>\r\n"
		+ "    <style>\r\n"
		+ "        body {\r\n"
		+ "            background-color: #F6F6F6;\r\n"
		+ "            margin: 0;\r\n"
		+ "            padding: 0;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        h1,\r\n"
		+ "        h2,\r\n"
		+ "        h3,\r\n"
		+ "        h4,\r\n"
		+ "        h5,\r\n"
		+ "        h6 {\r\n"
		+ "            margin: 0;\r\n"
		+ "            padding: 0;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        p {\r\n"
		+ "            margin: 0;\r\n"
		+ "            padding: 0;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .container {\r\n"
		+ "            width: 80%;\r\n"
		+ "            margin-right: auto;\r\n"
		+ "            margin-left: auto;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .brand-section {\r\n"
		+ "            background-color: #0d1033;\r\n"
		+ "            padding: 10px 40px;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .logo {\r\n"
		+ "            width: 50%;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .row {\r\n"
		+ "            display: flex;\r\n"
		+ "            flex-wrap: wrap;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .col-6 {\r\n"
		+ "            width: 50%;\r\n"
		+ "            flex: 0 0 auto;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .text-white {\r\n"
		+ "            color: #fff;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .company-details {\r\n"
		+ "            float: right;\r\n"
		+ "            text-align: right;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .body-section {\r\n"
		+ "            padding: 16px;\r\n"
		+ "            border: 1px solid gray;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .heading {\r\n"
		+ "            font-size: 20px;\r\n"
		+ "            margin-bottom: 08px;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .sub-heading {\r\n"
		+ "            color: #262626;\r\n"
		+ "            margin-bottom: 05px;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        table {\r\n"
		+ "            background-color: #fff;\r\n"
		+ "            width: 100%;\r\n"
		+ "            border-collapse: collapse;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        table thead tr {\r\n"
		+ "            border: 1px solid #111;\r\n"
		+ "            background-color: #f2f2f2;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        table td {\r\n"
		+ "            vertical-align: middle !important;\r\n"
		+ "            text-align: center;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        table th,\r\n"
		+ "        table td {\r\n"
		+ "            padding-top: 08px;\r\n"
		+ "            padding-bottom: 08px;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .table-bordered {\r\n"
		+ "            box-shadow: 0px 0px 5px 0.5px gray;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .table-bordered td,\r\n"
		+ "        .table-bordered th {\r\n"
		+ "            border: 1px solid #dee2e6;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .text-right {\r\n"
		+ "            text-align: end;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .w-20 {\r\n"
		+ "            width: 20%;\r\n"
		+ "        }\r\n"
		+ "\r\n"
		+ "        .float-right {\r\n"
		+ "            float: right;\r\n"
		+ "        }\r\n"
		+ "    </style>\r\n"
		+ "</head>\r\n"
		+ "\r\n"
		+ "<body>\r\n"
		+ "\r\n"
		+ "    <div class='container'>\r\n"
		+ "        <div class='brand-section'>\r\n"
		+ "            <div class='row'>\r\n"
		+ "                <div class='col-6'>\r\n"
		+ "                    <h1 class='text-white'>GodWatch</h1>\r\n"
		+ "                </div>\r\n"
		+ "                <div class='col-6'>\r\n"
		+ "                    <div class='company-details'>\r\n"
		+ "                        <p class='text-white'>Công viên phần mềm, Toà nhà Innovation lô 24, Quang Trung</p>\r\n"
		+ "                        <p class='text-white'>Quận 12, Thành phố Hồ Chí Minh</p>\r\n"
		+ "                        <p class='text-white'>Hotline: 0366 888 470</p>\r\n"
		+ "                    </div>\r\n"
		+ "                </div>\r\n"
		+ "            </div>\r\n"
		+ "        </div>\r\n"
		+ "\r\n"
		+ "        <div class='body-section'>\r\n"
		+ "            <div class='row'>\r\n"
		+ "                <div class='col-6'>\r\n"
		+ "                    <h2 class='heading'>Invoice: " + afterSave.getId() + "</h2>\r\n"
		+ "                    <p class='sub-heading'>Order Date: " + order.getCreateDate() + "</p>\r\n"
		+ "                    <p class='sub-heading'>Email Address: " + userMail + " </p>\r\n"
		+ "                </div>\r\n"
		+ "                <div class='col-6'>\r\n"
		+ "                    <p class='sub-heading'>Name: " +userDAO.findByAccountUsername(order.getAccount().getUsername()).getFullname()+"</p>\r\n"
		+ "                    <p class='sub-heading'>Address: " + order.getAddress() + "</p>\r\n"
		+ "                    <p class='sub-heading'>Phone: " + userDAO.findByAccountUsername(order.getAccount().getUsername()).getPhone() +"</p>\r\n"
		+ "                    <p class='sub-heading'>Payment Mode: " + orderMethodDAO.getById(order.getOrderMethod().getId()).getName() +"</p>\r\n"
		+ "                </div>\r\n"
		+ "            </div>\r\n"
		+ "        </div>\r\n"
		+ "\r\n"
		+ "        <div class='body-section'>\r\n"
		+ "            <h3 class='heading'>Ordered Items</h3>\r\n"
		+ "            <br>\r\n"
		+ "            <table class='table-bordered'>\r\n"
		+ "                <thead>\r\n"
		+ "                    <tr>\r\n"
		+ "                        <th>Product</th>\r\n"
		+ "                        <th class='w-20'>Price</th>\r\n"
		+ "                        <th class='w-20'>Quantity</th>\r\n"
		+ "                        <th class='w-20'>Total</th>\r\n"
		+ "                    </tr>\r\n"
		+ "                </thead>\r\n"
		+ "                <tbody>\r\n").append(renderListProduct).append(
		 "                    <tr>\r\n"
		+ "                        <td colspan='3' class='text-right' style='font-weight: bold;'>Grand Total </td>\r\n"
		+ "                        <td>" + totalBill + "$</td>\r\n"
		+ "                    </tr>\r\n"
		+ "                </tbody>\r\n"
		+ "            </table>\r\n"
		+ "        </div>\r\n"
		+ "\r\n"
		+ "        <div class='body-section'>\r\n"
		+ "            <p>&copy; Copyright 2022 - GodWatch. All rights reserved.\r\n"
		+ "                <a href='https://www.godwatch.com/' class='float-right'>www.godwatch.com</a>\r\n"
		+ "            </p>\r\n"
		+ "        </div>\r\n"
		+ "    </div>\r\n"
		+ "\r\n"
		+ "</body>\r\n"
		+ "\r\n"
		+ "</html>");
	try {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
		message.setSubject(emailSubject, "UTF-8");
//		message.setText(emailContent, "UTF-8", "html");
		message.setText(strB.toString(), "UTF-8", "html");
		// message.setContent(emailContent, "text/html; charset=utf-8");
		Transport.send(message);
	} catch (Exception e) {
	    	System.out.println("Purchase Invoice Failed to Email: " + userMail);
		e.printStackTrace();
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

    @Override
    public Order updateCancel(Order o) {
	// TODO Auto-generated method stub
	Order oldOder = o;
	oldOder.setOrderStatus(osDAO.getById(5));
	return orderDAO.save(oldOder);
    }

    @Override
    public Order update(Order o) {
	// TODO Auto-generated method stub
	return orderDAO.save(o);
    }

    @Override
    public List<Order> findAllOrderBySearch(String kw) {
	// TODO Auto-generated method stub
	return orderDAO.findAllOrderBySearch("%" + kw + "%");
    }

}
