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
public class ProductDiscountDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long productId;
    private String productName;
    private Double productPrice;
    private Integer productDiscount = 0;
    private Integer productEvaluation = 0;
    private String productImage;
    private Date endDate;
    private String productDetail;

}
