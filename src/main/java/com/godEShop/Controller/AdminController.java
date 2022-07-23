package com.godEShop.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.godEShop.Dao.ProductDAO;
import com.godEShop.Dao.UserDAO;
import com.godEShop.Dto.ProductImageDto;
import com.godEShop.Dto.ProductsStatisticDto;
import com.godEShop.Dto.UsersStatisticDto;
import com.godEShop.Utils.ProductsExcelWriter;
import com.godEShop.Utils.UsersExcelWriter;

@Controller
public class AdminController {
	@Autowired
	ProductDAO prodDAO;
	
	@Autowired
	UserDAO userDAO;
	private String title = "";
	private String keyword = "";
	private String date = "";
	
	@GetMapping("/export/products")
	public void exportProducts(HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	         
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ProductsReport_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
			        
		title = "Products Report".toUpperCase();
		keyword = "All products";
		date = "All time";
		List<ProductsStatisticDto> listProducts = prodDAO.getProductStatistic();
		List<ProductImageDto> listProductImage = prodDAO.getProductImage();
			         
		ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
			         
		excelExporter.export(response);  
	}
	
	@GetMapping("/export/products/start={start}&end={end}")
	public void exportProducts(HttpServletResponse response, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException, ParseException{
		response.setContentType("application/octet-stream");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	         
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ProductsReport_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
			         
		Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
		List<ProductsStatisticDto> listProducts = prodDAO.getProductStatisticByTime(newStart, newEnd);
		List<ProductImageDto> listProductImage = prodDAO.getProductImage();
			         
		title = "Products Report".toUpperCase();
		keyword = "All products";
		date = "From " + start + " to " + end;
		ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
			         
		excelExporter.export(response);  
	}
	
	@GetMapping("/export/products/name={name}")
	public void exportProductsByName(HttpServletResponse response, @PathVariable("name") String name) throws IOException{
		response.setContentType("application/octet-stream");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	         
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ProductsReport_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
			         
		List<ProductsStatisticDto> listProducts = prodDAO.get1ProductStatistic("%" + name + "%");
		List<ProductImageDto> listProductImage = prodDAO.getProductImage();
		
		title = "Products Report".toUpperCase();
		keyword = "Filter by " + name;
		date = "All time";
		ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
			         
		excelExporter.export(response);  
	}
	
	@GetMapping("/export/products/name={name}/start={start}&end={end}")
	public void exportProductsByName(HttpServletResponse response, @PathVariable("name") String name, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException, ParseException{
		response.setContentType("application/octet-stream");
	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	    String currentDateTime = dateFormatter.format(new Date());
	         
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ProductsReport_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
			         
		Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
		title = "Products Report".toUpperCase();
		keyword = "Filter by " + name;
		date = "From " + start + " to " + end;
		List<ProductsStatisticDto> listProducts = prodDAO.get1ProductStatisticByTime("%" + name + "%", newStart, newEnd);
		List<ProductImageDto> listProductImage = prodDAO.getProductImage();
			         
		ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
			         
		excelExporter.export(response);  
	}
	
	@GetMapping("/export/users")
	public void exportUsers(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=UsersReport_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<UsersStatisticDto> listUsers = userDAO.getAllUserStat();
         
        title = "Users Report".toUpperCase();
		keyword = "All users";
		date = "All time";
        UsersExcelWriter excelExporter = new UsersExcelWriter(title, keyword, date, listUsers);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/users/start={start}&end={end}")
	public void exportUsers(HttpServletResponse response, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException,ParseException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=UsersReport_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        List<UsersStatisticDto> listUsers = userDAO.getAllUserStatByTime(newStart, newEnd);
         
        title = "Users Report".toUpperCase();
		keyword = "All users";
		date = "From " + start + " to " + end;
        UsersExcelWriter excelExporter = new UsersExcelWriter(title, keyword, date, listUsers);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/users/name={name}")
	public void exportUsersByName(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=UsersReport_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<UsersStatisticDto> listUsers = userDAO.find1UserStat("%" + name + "%");
         
        title = "Users Report".toUpperCase();
		keyword = "Filter by" + name; 
		date = "All time";
        UsersExcelWriter excelExporter = new UsersExcelWriter(title, keyword, date, listUsers);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/users/name={name}/start={start}&end={end}")
	public void exportUsersByName(HttpServletResponse response, @PathVariable("name") String name, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException,ParseException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=UsersReport_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        List<UsersStatisticDto> listUsers = userDAO.find1UserStatByTime("%" + name + "%", newStart, newEnd);
         
        title = "Users Report".toUpperCase();
		keyword = "Filter by " + name; 
		date = "From " + start + " to " + end;
        UsersExcelWriter excelExporter = new UsersExcelWriter(title, keyword, date, listUsers);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/productsByMale")
	public void exportUsersByMale(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BestSellerByMale_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<ProductsStatisticDto> listProducts = prodDAO.getBestProductStatisticByMale(1);
        List<ProductImageDto> listProductImage = prodDAO.getProductImage();
         
        ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/productsByMale/start={start}&end={end}")
	public void exportProductsByMale(HttpServletResponse response, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException, ParseException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BestSellerByMale_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
		int gender = 1;
        List<ProductsStatisticDto> listProducts = prodDAO.getBestProductStatisticByMaleByTime(gender, newStart, newEnd);
        List<ProductImageDto> listProductImage = prodDAO.getProductImage();
         
        ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/productsByFemale")
	public void exportProductsByFemale(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BestSellerByFemale_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<ProductsStatisticDto> listProducts = prodDAO.getBestProductStatisticByMale(0);
        List<ProductImageDto> listProductImage = prodDAO.getProductImage();
         
        ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
         
        excelExporter.export(response);  
	}
	
	@GetMapping("/export/productsByFemale/start={start}&end={end}")
	public void exportProductsByFemale(HttpServletResponse response, @PathVariable("start") String start, @PathVariable("end") String end) throws IOException, ParseException {
		response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BestSellerByFemale_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        Date newStart = new SimpleDateFormat("yyyy-MM-dd").parse(start);
		Date newEnd = new SimpleDateFormat("yyyy-MM-dd").parse(end);
		int gender = 0;
        List<ProductsStatisticDto> listProducts = prodDAO.getBestProductStatisticByMaleByTime(gender, newStart, newEnd);
        List<ProductImageDto> listProductImage = prodDAO.getProductImage();
         
        ProductsExcelWriter excelExporter = new ProductsExcelWriter(title, keyword, date, listProducts, listProductImage);
         
        excelExporter.export(response);  
	}
	
}
