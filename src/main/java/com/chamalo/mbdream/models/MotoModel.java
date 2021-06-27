package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

    @Column(nullable = false, length = 250, unique = true)
    private String slugMoto;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "Le nom dois être remplis")
    private String nomMoto;

    @Column(columnDefinition = "TEXT")
    private String descriptionMoto;

    @Column(columnDefinition = "DATE", nullable = false)
    private Instant dateAjout;

    @Column(columnDefinition = "TEXT")
    private String backgroundImgMoto;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private MarqueModel marque;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private CategorieModel categorie;

    @OneToMany(mappedBy = "moto", cascade = CascadeType.REMOVE)
    private Collection<MediaModel> medias;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "info_id")
    private InfoModel infos;
}
