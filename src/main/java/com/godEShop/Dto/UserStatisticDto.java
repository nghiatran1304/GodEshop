package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.godEShop.Entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserStatisticDto implements Serializable{
	private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String username;
    private String fullname;
    private String image;
    private Date createDate;
    private double price;
    private int quantity;
    private OrderStatus orderStatus;
}
