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

public class ProductAccessoryInfoDto implements Serializable{
	
	
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
	    private Integer accessoryId;
	    private String accessoryColor;
	   
	    private String bmName;
	    private String brandName;
	    private String categoryName;

	    private Integer braceletMaterialId;
	
	    
}
