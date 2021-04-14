package com.chamalo.mbdream.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for request when you want to add a Category
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieRequest {
    private String slugCategorie;

    private String nomCategorie;
}
