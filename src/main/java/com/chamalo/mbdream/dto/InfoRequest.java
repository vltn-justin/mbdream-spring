package com.chamalo.mbdream.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for request when you want to add Info to moto
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoRequest {
    private String slugMoto;

    private Integer prix;

    private String architectureMoteur;

    private String cylindre;

    private Integer puissance;

    private Integer couple;

    private Integer poid;

    private Double capaciteReservoir;

    private Double consommation;
}
