package com.godEShop.Dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import lombok.ToString;

@ToString
public class ProductShopDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long productId;
    private Integer productCategoryId;
    private String productName;
    private Double productPrice;
    private Date productCreateDate;
    private String productCategoryName;
    private String productFirstImage;
    private Integer productEvaluation = null;
    private Integer productDiscount;
    private String productDetail;
    private Date productEndDiscount;
    private Integer productQuantity;
    private Date productStartDiscount;

    public ProductShopDto(Long productId, Integer productCategoryId, String productName, Double productPrice,
	    Date productCreateDate, String productCategoryName, String productFirstImage, Integer productEvaluation,
	    Integer productDiscount, String productDetail, Date productEndDiscount, Integer productQuantity,
	    Date productStartDiscount) {
	this.productId = productId;
	this.productCategoryId = productCategoryId;
	this.productName = productName;
	this.productPrice = productPrice;
	this.productCreateDate = productCreateDate;
	this.productCategoryName = productCategoryName;
	this.productFirstImage = productFirstImage;
	this.productEvaluation = productEvaluation;
	this.productDiscount = productDiscount;
	this.productDetail = productDetail;
	this.productEndDiscount = productEndDiscount;
	this.productQuantity = productQuantity;
	this.productStartDiscount = productStartDiscount;
    }

    public ProductShopDto() {

    }

    public double getFinalPrice() {
	Date nowTime = new Date();

	if (this.productEndDiscount == null || this.productStartDiscount.compareTo(nowTime) == 1) {
	    return this.productPrice;
	}
	if (this.productEndDiscount.compareTo(nowTime) == -1) {
	    return this.productPrice;
	}
	double p = this.productPrice;
	Integer d = this.productDiscount == null ? 0 : this.productDiscount;
	double rs = p - (p * d / 100.0);
	return rs;
    }

    public Long getProductId() {
	return productId;
    }

    public void setProductId(Long productId) {
	this.productId = productId;
    }

    public Integer getProductCategoryId() {
	return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
	this.productCategoryId = productCategoryId;
    }

    public String getProductName() {
	return productName;
    }

    public void setProductName(String productName) {
	this.productName = productName;
    }

    public Double getProductPrice() {
	return productPrice;
    }

    public void setProductPrice(Double productPrice) {
	this.productPrice = productPrice;
    }

    public Date getProductCreateDate() {
	return productCreateDate;
    }

    public void setProductCreateDate(Date productCreateDate) {
	this.productCreateDate = productCreateDate;
    }

    public String getProductCategoryName() {
	return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
	this.productCategoryName = productCategoryName;
    }

    public String getProductFirstImage() {
	return productFirstImage;
    }

    public void setProductFirstImage(String productFirstImage) {
	this.productFirstImage = productFirstImage;
    }

    public Integer getProductEvaluation() {
	return productEvaluation;
    }

    public void setProductEvaluation(Integer productEvaluation) {
	this.productEvaluation = productEvaluation;
    }

    public Integer getProductDiscount() {
	return productDiscount;
    }

    public void setProductDiscount(Integer productDiscount) {
	this.productDiscount = productDiscount;
    }

    public String getProductDetail() {
	return productDetail;
    }

    public void setProductDetail(String productDetail) {
	this.productDetail = productDetail;
    }

    public Date getProductEndDiscount() {
	return productEndDiscount;
    }

    public void setProductEndDiscount(Date productEndDiscount) {
	this.productEndDiscount = productEndDiscount;
    }

    public Integer getProductQuantity() {
	return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
	this.productQuantity = productQuantity;
    }

    public Date getProductStartDiscount() {
	return productStartDiscount;
    }

    public void setProductStartDiscount(Date productStartDiscount) {
	this.productStartDiscount = productStartDiscount;
    }

}
