package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Collection;

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
}
