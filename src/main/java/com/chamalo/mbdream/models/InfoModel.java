package com.chamalo.mbdream.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String cylindre;

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

    @OneToOne
    private MotoModel moto;
}
