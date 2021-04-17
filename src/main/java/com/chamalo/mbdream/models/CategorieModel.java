package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * Model for Categorie
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Categorie")
@Table(name = "categorie")
public class CategorieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;

    @Column(nullable = false, length = 250, unique = true)
    @Value("")
    private String slugCategorie;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Le nom de la catégorie doit être remplis")
    private String nomCategorie;

    @OneToMany(mappedBy = "categorie")
    private Collection<MotoModel> motos;
}