package com.chamalo.mbdream.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
