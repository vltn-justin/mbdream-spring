package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Media")
@Table(name = "media")
public class MediaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedia;

    @Column
    private String lienMedia;

    @Column(columnDefinition = "TEXT")
    private String descriptionMedia;

    @Column
    private Boolean isVideo;

    @ManyToOne
    @JoinColumn(name = "moto_id")
    private MotoModel moto;
}
