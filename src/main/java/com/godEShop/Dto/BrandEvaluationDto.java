package com.godEShop.Dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandEvaluationDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Integer brandId;
    private String brandName;
    private Long productId;
    private Double productPrice;
    private String productImage;
    private Integer evaluation;
    private Integer discount = null;    
    
}
