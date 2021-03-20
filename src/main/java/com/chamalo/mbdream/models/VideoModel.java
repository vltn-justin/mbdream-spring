package com.chamalo.mbdream.models;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model for Video
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Video")
@Table(name = "video")
public class VideoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVideo;

    @Column
    private String lienVideo;

    @Column(columnDefinition = "TEXT")
    private String descriptionVideo;

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

        map.put("idVideo", this.idVideo);
        map.put("lienVideo", this.lienVideo);
        map.put("description", this.descriptionVideo);
        map.put("moto", this.moto.getNomMoto());

        return map;
    }
}
