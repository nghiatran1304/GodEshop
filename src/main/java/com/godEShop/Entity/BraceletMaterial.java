package com.godEShop.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the BraceletMaterials database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BraceletMaterials")
public class BraceletMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

    // bi-directional many-to-one association to Accessory
    @JsonIgnore
    @OneToMany(mappedBy = "braceletMaterial", cascade = CascadeType.ALL)
    private List<Accessory> accessories;

    // bi-directional many-to-one association to Watch
    @JsonIgnore
    @OneToMany(mappedBy = "braceletMaterial", cascade = CascadeType.ALL)
    private List<Watch> watches;

}