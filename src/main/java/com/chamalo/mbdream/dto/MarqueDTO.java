package com.chamalo.mbdream.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for request when you want to add a Marque
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarqueDTO {
    private String slugMarque;

    private String nomMarque;

    private String descriptionMarque;

    private String logoMarque;

    private java.sql.Date dateCreation;
}
