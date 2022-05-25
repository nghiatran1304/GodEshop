package com.godEShop.Dto;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessoryDto implements Serializable{

    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long productId;
    private String productName;
    private String categoryName;
    private String brandName;
    private String madeIn;
    private Integer productWarranty;
    private String braceletMaterial;
    private String color;

}
