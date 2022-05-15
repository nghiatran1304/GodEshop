package com.godEShop.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the ProductDiscounts database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductDiscounts")
public class ProductDiscount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Createdate")
    private Date createDate;

    @Column(name = "Discount")
    private Integer discount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Enddate")
    private Date endDate;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "CreateBy")
    private Account account;

    // bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

}