package com.chamalo.mbdream.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for request when you want to add a Moto
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoRequest {
    private String slugMoto;

    private String nomMoto;

    private String descriptionMoto;

    private boolean isFeatured;

    private String slugMarque;

    private String slugCategorie;
}
