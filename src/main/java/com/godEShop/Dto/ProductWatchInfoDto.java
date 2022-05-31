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
public class ProductWatchInfoDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long productId;    
    private String productName;
    private Boolean productIsDeteled;
    private Integer productQuantity;
    private Double productPrice;
    private Date productCreateDate;
    private Integer productWarranty;
    private String productMadeIn;
    private String productDetail;
    private Integer brandId;
    private Integer categoryId;
    
    //
    private String imageId;
    //
    private Integer watchId;
    private Integer watchGender;
    private Integer watchGlassSize;
    private Integer watchATM;
    private String watchGlassColor;
    private String watchCaseColor;
    private Integer glassMaterialId;
    private Integer braceletMaterialId;
    private Integer machineInsideId;
    
    private String brandName;
    private String categoryName;
    private String gmName;
    private String bmName;
    private String miName;
    
    
    
    
    
}
