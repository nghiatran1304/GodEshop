package com.godEShop.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the ProductPhotos database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductPhotos")
public class ProductPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private String id;

    // bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

}