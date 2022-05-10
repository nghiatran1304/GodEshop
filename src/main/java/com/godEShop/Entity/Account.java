package com.godEShop.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Accounts database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username")
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "IsDeleted")
    private boolean isDeleted;

    @Column(name = "Password")
    private String password;

    // bi-directional many-to-one association to Authority
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Authority> authorities;

    // bi-directional many-to-one association to Gcoin
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Gcoin> gcoins;

    // bi-directional many-to-one association to History
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<History> histories;

    // bi-directional many-to-one association to Order
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Order> orders;

    // bi-directional many-to-one association to ProductComment
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductComment> productComments;

    // bi-directional many-to-one association to ProductDiscount
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductDiscount> productDiscounts;

    // bi-directional many-to-one association to ProductEvaluation
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductEvaluation> productEvaluations;

    // bi-directional many-to-one association to ProductLike
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductLike> productLikes;

    // bi-directional many-to-one association to ProductReply
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<ProductReply> productReplies;

    // bi-directional many-to-one association to RefAccount
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<RefAccount> refAccounts;

    // bi-directional many-to-one association to User
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<User> users;

    // bi-directional many-to-one association to VoucherList
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<VoucherList> voucherLists;

    // bi-directional many-to-one association to Voucher
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Voucher> vouchers;

}