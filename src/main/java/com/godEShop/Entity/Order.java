package com.godEShop.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Orders database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Address")
    private String address;
    
    @Column(name = "Notes")
    private String note;

    @Column(name = "Createdate")
    private Date createDate;

    // bi-directional many-to-one association to OrderDetail
    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "Username")
    private Account account;

    // bi-directional many-to-one association to OrderMethod
    @ManyToOne
    @JoinColumn(name = "OrdermethodId")
    private OrderMethod orderMethod;

    // bi-directional many-to-one association to OrderStatus
    @ManyToOne
    @JoinColumn(name = "OrderstatusId")
    private OrderStatus orderStatus;
}