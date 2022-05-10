package com.godEShop.Entity;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the ProductComments database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ProductComments")
public class ProductComment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CommentContent")
    private String commentContent;

    // bi-directional many-to-one association to CommentPhoto
    @JsonIgnore
    @OneToMany(mappedBy = "productComment")
    private List<CommentPhoto> commentPhotos;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "TopicUsername")
    private Account account;

    // bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    // bi-directional many-to-one association to ProductReply
    @JsonIgnore
    @OneToMany(mappedBy = "productComment")
    private List<ProductReply> productReplies;

}