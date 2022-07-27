package com.godEShop.Dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RevenueAndOrders implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private double totalRevenueToday;
	private int totalOrdersToday;
	private double totalRevenueYesterday;
	private int totalOrdersYesterday;
}
