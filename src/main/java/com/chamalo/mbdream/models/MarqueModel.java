package com.chamalo.mbdream.models;

import com.chamalo.mbdream.utils.MethodUtils;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.*;

/**
 * Model for Marque
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Marque")
@Table(name = "marque")
public class MarqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarque;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String slugMarque;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Le nom dois être remplis")
    private String nomMarque;

    @Column(columnDefinition = "DATE")
    private Date dateCreation;

    @Column(columnDefinition = "TEXT")
    private String descriptionMarque;

    @Column
    private String logoMarque;

    @OneToMany(mappedBy = "marque")
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
     * Method to remove a Moto in motos
     *
     * @param moto Moto to remove
     */
    public void removeMoto (final MotoModel moto) {
        motos.remove(moto);
    }

    /**
     * Method to send a custom JSON Response
     *
     * @return Map
     */
    @JsonValue
    public Map<String, Object> toJson () {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("idMarque", this.idMarque);
        map.put("slugMarque", this.slugMarque);
        map.put("nomMarque", this.nomMarque);
        map.put("dateCreation", this.dateCreation);
        map.put("descriptionMarque", this.descriptionMarque);
        map.put("logoMarque", this.logoMarque);

        map.put("motos", MethodUtils.extractMoto(this.motos));

        return map;
    }
}
