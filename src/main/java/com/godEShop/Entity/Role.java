package com.godEShop.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Roles database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private String id;

    @Column(name = "Name")
    private String name;

    // bi-directional many-to-one association to Account
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Account> accounts;

}