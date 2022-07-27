package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RevenueByMonthDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date createDate;
	private double totalRevenue;
}
