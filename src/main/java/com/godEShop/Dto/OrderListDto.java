package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderListDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Integer orderId;
	private Date createDate;
	private String address;
	private String statusName;
}
