package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOnSaleDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer productDiscountId;
    private Date productDiscountCreateDate;
    private Integer productDiscountPercent;
    private Date productDiscountEndDate;
    
    private String username;
    
    private Long productId;
    private String productName;
    private Double productPrice;    
    
      
    
}
