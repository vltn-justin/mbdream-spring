package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Model for Info
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Info")
@Table(name = "info")
public class InfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInfo;

    @Column
    private Integer prix;

    @Column
    private String architectureMoteur;

    @Column
    private Integer cylindre;

    @Column
    private Integer puissance;

    @Column
    private Integer couple;

    @Column
    private Integer poid;

    @Column
    private Double capaciteReservoir;

    @Column
    private Double consommation;

    @OneToOne(mappedBy = "infos", cascade = CascadeType.PERSIST)
    private MotoModel moto;
}
