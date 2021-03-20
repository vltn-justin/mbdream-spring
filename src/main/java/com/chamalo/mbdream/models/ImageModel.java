package com.chamalo.mbdream.models;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model for Image
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Image")
@Table(name = "image")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImage;

    @Column
    private String lienImage;

    @Column(columnDefinition = "TEXT")
    private String descriptionImage;

    @ManyToOne
    @JoinColumn(name = "moto_id")
    private MotoModel moto;

    /**
     * Method to send a custom JSON Response
     *
     * @return Map
     */
    @JsonValue
    public Map<String, Object> toJson () {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("idImage", this.idImage);
        map.put("lienImage", this.lienImage);
        map.put("description", this.descriptionImage);
        map.put("moto", this.moto.getNomMoto());

        return map;
    }
}
