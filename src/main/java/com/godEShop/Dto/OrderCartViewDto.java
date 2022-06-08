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
public class OrderCartViewDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long orderId;
	private String productPhoto;
	private String productName;
	private Double orderPrice;
	private Integer orderQuantity;
	private Date orderCreateDate;
	private String orderAddress;
	private String orderStatus;
}
