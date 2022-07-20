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
public class ProductsExcel implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    private Long productId;
    private String productName;
    private String photo;
    private int quantityleft;
    private Long quantity;
}
