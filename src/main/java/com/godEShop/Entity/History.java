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
 * The persistent class for the Histories database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Histories")
public class History implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "DetailKey")
    private String detailKey;

    @Column(name = "DetailValue")
    private String detailValue;

    @Column(name = "IdObject")
    private String idObject;

    @Column(name = "ObjectName")
    private String objectName;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "Username")
    private Account account;

}