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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Vouchers database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Vouchers")
public class Voucher implements Serializable {
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

    @Column(name = "Name")
    private String name;

    // bi-directional many-to-one association to VoucherList
    @JsonIgnore
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<VoucherList> voucherLists;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "CreateBy")
    private Account account;

}