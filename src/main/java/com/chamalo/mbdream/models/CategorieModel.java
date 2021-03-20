package com.chamalo.mbdream.models;

import com.chamalo.mbdream.utils.MethodUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    @Value("")
    private String slugCategorie;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Le nom de la catégorie doit être remplis")
    private String nomCategorie;

    @OneToMany(mappedBy = "categorie")
    private Collection<MotoModel> motos;

    /**
     * Method to add a Moto in motos
     *
     * @param moto Moto to add
     */
    public void addMoto (final MotoModel moto) {
        this.motos.add(moto);
    }

    /**
     * Method to send a custom JSON Response
     *
     * @return Map
     */
    @JsonValue
    public Map<String, Object> toJson () {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("idCategorie", this.idCategorie);
        map.put("slugCategorie", this.slugCategorie);
        map.put("nomCategorie", this.nomCategorie);

        map.put("motos", MethodUtils.extractMoto(this.motos));

        return map;
    }
}