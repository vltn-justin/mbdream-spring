package com.chamalo.mbdream.models;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model for Moto
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Moto")
@Table(name = "moto")
public class MotoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMoto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String slugMoto;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Le nom dois être remplis")
    private String nomMoto;

    @Column
    private Integer prixMoto;

    @Column(columnDefinition = "TEXT")
    private String descriptionMoto;

    @Column(columnDefinition = "DATE", nullable = false)
    private Instant dateAjout;

    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean isFeatured;

    @Column(columnDefinition = "TEXT")
    private String backgroundImgMoto;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private MarqueModel marque;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieModel categorie;

    @OneToMany(mappedBy = "moto")
    private Collection<ImageModel> images;

    @OneToMany(mappedBy = "moto")
    private Collection<VideoModel> videos;

    @OneToOne
    private InfoModel infos;

    /**
     * Method to add an image in images
     *
     * @param image Image to add
     */
    public void addImage (final ImageModel image) {
        this.images.add(image);
    }

    /**
     * Method to add a video in videos
     *
     * @param video Video to add
     */
    public void addVideo (final VideoModel video) {
        this.videos.add(video);
    }

    /**
     * Method to send a custom JSON Response
     *
     * @return Map
     */
    @JsonValue
    public Map<String, Object> toJson () {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("idMoto", this.idMoto);
        map.put("slugMoto", this.slugMoto);
        map.put("nomMoto", this.nomMoto);
        map.put("prixMoto", this.prixMoto);
        map.put("descriptionMoto", this.descriptionMoto);
        map.put("backgroundImgMoto", this.backgroundImgMoto);

        if (this.marque != null) {
            Map<String, String> marqueMap = new LinkedHashMap<>();

            marqueMap.put("nomMarque", this.marque.getNomMarque());
            marqueMap.put("slugMarque", this.marque.getSlugMarque());

            map.put("marque", marqueMap);
        } else {
            map.put("marque", null);
        }

        if (this.categorie != null) {
            Map<String, String> categorieMap = new LinkedHashMap<>();

            categorieMap.put("nomCategorie", this.categorie.getNomCategorie());
            categorieMap.put("slugCategorie", this.categorie.getSlugCategorie());

            map.put("categorie", categorieMap);
        } else {
            map.put("categorie", null);
        }

        map.put("dateAjout", this.dateAjout);
        map.put("isFeatured", this.isFeatured);

        map.put("nbImages", this.images.size());
        map.put("nbVideos", this.videos.size());

        return map;
    }
}
