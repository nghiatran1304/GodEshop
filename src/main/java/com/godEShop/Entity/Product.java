package com.godEShop.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Products database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    private Date createDate;

    @Column(name = "Detail")
    private String detail;

    @Column(name = "Madein")
    private String madeIn;

    @Column(name = "Name")
    private String name;

    @Column(name = "Isdeleted")
    private Boolean isDeleted = false;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Warranty")
    private Integer warranty;

    // bi-directional many-to-one association to Accessory
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Accessory> accessories;

    // bi-directional many-to-one association to OrderDetail
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    // bi-directional many-to-one association to ProductComment
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductComment> productComments;

    // bi-directional many-to-one association to ProductDiscount
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductDiscount> productDiscounts;

    // bi-directional many-to-one association to ProductEvaluation
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductEvaluation> productEvaluations;

    // bi-directional many-to-one association to ProductLike
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductLike> productLikes;

    // bi-directional many-to-one association to ProductPhoto
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductPhoto> productPhotos;

    // bi-directional many-to-one association to Brand
    @ManyToOne
    @JoinColumn(name = "BrandId")
    private Brand brand;

    // bi-directional many-to-one association to Category
    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

    // bi-directional many-to-one association to Watch
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Watch> watches;

}