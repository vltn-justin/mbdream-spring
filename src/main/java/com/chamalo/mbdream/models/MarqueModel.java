package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @Column(nullable = false, length = 250, unique = true)
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
}
