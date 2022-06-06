package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDto implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private Long orderId;
    private Integer orderStatusId;
    private String orderUsername;
    private Date orderCreateDate;
    private String userFullname;
    private String userPhone;
    private String userEmail;
    
    private String orderMethodName;
    private String orderAddress;
    private String orderNotes;
    
    private String productPhotoImage;
    
    private String productName;
    	
    private Double orderDetailPrice;
    private Integer orderDetailQuantity;
    
    
}
