package com.godEShop.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.godEShop.Dto.OrderInfoDto;
import com.godEShop.Entity.Order;
import com.godEShop.Entity.OrderDetail;
import com.godEShop.Entity.OrderStatus;
import com.godEShop.Entity.Product;
import com.godEShop.Service.OrderDetailService;
import com.godEShop.Service.OrderMethodService;
import com.godEShop.Service.OrderService;
import com.godEShop.Service.OrderStatusService;
import com.godEShop.Service.ProductService;
import com.godEShop.Service.UserService;

@CrossOrigin("*")
@RestController
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMethodService orderMethodSevice;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderStatusService orderStatusService;

    @Autowired
    UserService userService;

    @PostMapping("/rest/orders")
    public Order create(@RequestBody JsonNode orderData) {
	return orderService.create(orderData);
    }

    @GetMapping("/rest/allOrders")
    public List<Order> allOrder() {
	return orderService.findAllOrders();
    }

    @GetMapping("/rest/order-pending")
    public List<Order> orderPending() {
	return orderService.findAllOrderPending();
    }

    @GetMapping("/rest/order-confirmed")
    public List<Order> orderConfirm() {
	return orderService.findAllOrderConfirmed();
    }

    @GetMapping("/rest/order-delivery")
    public List<Order> orderDelivery() {
	return orderService.findAllOrderDelivery();
    }

    @GetMapping("/rest/order-success")
    public List<Order> orderSuccess() {
	return orderService.findAllOrderSuccess();
    }

    @GetMapping("/rest/order-cancel")
    public List<Order> orderCancel() {
	return orderService.findAllOrderCancel();
    }

    @GetMapping("/rest/order-infoDto/{id}")
    public List<OrderInfoDto> lstOrderDto(@PathVariable("id") Long id) {
	return orderService.findAllOrderInfoDto(id);
    }

    @PutMapping("/rest/order-update-confirm/{id}")
    public Order updateConfirm(@PathVariable("id") Long id, @RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateConfirm(order);
    }

    @PutMapping("/rest/order-update-delivery/{id}")
    public Order updateDelivery(@PathVariable("id") Long id, @RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateDelivery(order);
    }

    @PutMapping("/rest/order-update-success/{id}")
    public Order updateSuccess(@PathVariable("id") Long id, @RequestBody OrderInfoDto o) {
	Order order = orderService.findById(id);
	return orderService.updateSuccess(order);
    }

    @PutMapping("/rest/admin-cancel/{id}")
    public Order adminCancelOrder(@PathVariable("id") Long id, @RequestBody String str) {
	List<OrderDetail> lstOd = orderDetailService.findAllProductByOrderDetailId(id);

	OrderStatus os = orderStatusService.findById(5);

	Order o = orderService.findById(lstOd.get(0).getOrder().getId());
	o.setOrderStatus(os);
	orderService.update(o);

	for (int i = 0; i < lstOd.size(); i++) {
	    Product p = productService.getById(lstOd.get(i).getProduct().getId());
	    int newQuantity = p.getQuantity() + lstOd.get(i).getQuantity();
	    p.setQuantity(newQuantity);
	    productService.update(p);
	}

	return o;
    }

    // xuất danh sách giao hàng
    @GetMapping("/rest/order-delivery/print")
    public void getListDelivery(HttpServletResponse response) {
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet worksheet;
	Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();

	LocalDateTime myDateObj = LocalDateTime.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	String formattedDate = myDateObj.format(myFormatObj);

	worksheet = workbook.createSheet("Delivery_" + formattedDate);
	// thêm test result vào file excel ở cột header
	CellStyle rowStyle = workbook.createCellStyle();
	rowStyle.setAlignment(HorizontalAlignment.CENTER);
	rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	rowStyle.setWrapText(true);

	data.put("1", new Object[] { "STT", "Order ID", "Fullname", "Address", "Phone", "Payment Method", "Customer's Sign" });

	List<Order> lstOrder = orderService.findAllOrderDelivery();

	for (int i = 0; i < lstOrder.size(); i++) {
	    String stt = String.valueOf((i + 1)).toString();
	    String orderId = String.valueOf(lstOrder.get(i).getId());
	    String fullname = userService.findByUsername(lstOrder.get(i).getAccount().getUsername()).getFullname();
	    String orderAddress = lstOrder.get(i).getAddress();
	    String phone = userService.findByUsername(lstOrder.get(i).getAccount().getUsername()).getPhone();
	    String paymentMethod = orderMethodSevice.getById(lstOrder.get(i).getOrderMethod().getId()).getName();
	    String sign = " ";

	    data.put(String.valueOf((i + 1) + 1).toString(),
		    new Object[] { stt, orderId, fullname, orderAddress, phone, paymentMethod, sign });
	}

	Set<String> keyset = data.keySet();
	int rownum = 0;
	for (String key : keyset) {
	    CellStyle rowStyleResult = workbook.createCellStyle();
	    Row row = worksheet.createRow(rownum++);
	    Object[] objArr = data.get(key);
	    int cellnum = 0;
	    for (Object obj : objArr) {
		Cell cell = row.createCell(cellnum++);
		if (obj instanceof Date) {
		    cell.setCellValue((Date) obj);
		} else if (obj instanceof Boolean) {
		    cell.setCellValue((Boolean) obj);
		} else if (obj instanceof String) {
		    cell.setCellValue((String) obj);
		} else if (obj instanceof Double) {
		    cell.setCellValue((Double) obj);
		}
	    }
	    worksheet.autoSizeColumn(cellnum);
	    worksheet.setColumnWidth(2, 5000);
	    worksheet.setColumnWidth(3, 20000);
	    worksheet.setColumnWidth(4, 5000);
	    worksheet.setColumnWidth(5, 7000);
	    worksheet.setColumnWidth(6, 5000);
	    rowStyleResult.setAlignment(HorizontalAlignment.CENTER);
	    rowStyleResult.setVerticalAlignment(VerticalAlignment.CENTER);
	    rowStyleResult.setWrapText(true);

	    row.setRowStyle(rowStyleResult);
	}

	try {
	    response.setContentType("application/octet-stream");
	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=" + "DeliveryList_" + formattedDate + ".xlsx";
	    response.setHeader(headerKey, headerValue);
	    
	    ServletOutputStream outputStream = response.getOutputStream();
	    workbook.write(outputStream);
	    workbook.close();
	    outputStream.close();

	    System.out.println("Successfully save to Excel File!!!");
	} catch (Exception e) {
	    System.out.println("Error save file excel delivery: " + e.getMessage());
	}

	//return workbook;
    }

}
