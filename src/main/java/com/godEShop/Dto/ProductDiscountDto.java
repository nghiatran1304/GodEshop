package com.godEShop.Dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long productId;
    private String productName;
    private String productPrice;
    private String productPriceAfterDiscount;
    private String productImage;
    private String endDate;
    private String productDetail;

}
