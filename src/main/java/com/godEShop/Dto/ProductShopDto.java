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
public class ProductShopDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long productId;
    private Integer productCategoryId;
    private String productName;
    private Double productPrice;
    private Date productCreateDate;
    private String productCategoryName;
    private String productFirstImage;
    private Integer productEvaluation = 0;
    private Integer productDiscount;
    private String productDetail;
    
}
