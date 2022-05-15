package com.godEShop.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the Watches database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Watches")
public class Watch implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ATM")
    private Integer atm;

    @Column(name = "Casecolors")
    private String caseColors;

    @Column(name = "Gender")
    private Integer gender;

    @Column(name = "Glasscolors")
    private String glassColors;

    @Column(name = "Glasssizes")
    private Integer glassSizes;

    // bi-directional many-to-one association to BraceletMaterial
    @ManyToOne
    @JoinColumn(name = "BraceletmaterialId")
    private BraceletMaterial braceletMaterial;

    // bi-directional many-to-one association to GlassMaterial
    @ManyToOne
    @JoinColumn(name = "GlassmaterialId")
    private GlassMaterial glassMaterial;

    // bi-directional many-to-one association to MachineInside
    @ManyToOne
    @JoinColumn(name = "MachineinsideId")
    private MachineInside machineInside;

    // bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

}