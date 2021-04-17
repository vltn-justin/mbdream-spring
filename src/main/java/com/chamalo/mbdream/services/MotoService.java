package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MotoRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.*;
import com.chamalo.mbdream.repositories.*;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class MotoService {

    private final MotoRepository motoRepository;
    private final MarqueRepository marqueRepository;
    private final CategorieRepository categorieRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public MotoService(final MotoRepository motoRepository,
                       final MarqueRepository marqueRepository,
                       final CategorieRepository categorieRepository,
                       final MediaRepository mediaRepository) {
        this.motoRepository = motoRepository;
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
        this.mediaRepository = mediaRepository;
    }

    /**
     * Method to get all moto, limited by 10
     *
     * @param page Page number
     *
     * @return Iterable of MotoModel
     */
    public Iterable<MotoModel> findAllMotoByPage(final Integer page) { return this.motoRepository.getMotoByPage(page); }

    /**
     * Method to get all moto featured
     *
     * @return Iterable of MotoModel
     */
    public Iterable<MotoModel> findAllFeaturedMoto() {
        return this.motoRepository.findAllFeaturedMoto();
    }

    /**
     * Method to count all moto inside database
     *
     * @return Long
     */
    public Long countAllMoto() {
        return this.motoRepository.count();
    }

    /**
     * Method to get a moto with is slug
     *
     * @param slug Slug of moto
     *
     * @return MotoModel or MBDreamException
     */
    public MotoModel findMotoBySlug(final String slug) {
        return this.motoRepository.findMotoBySlug(slug).orElseThrow(
                () -> new MBDreamException("Moto introuvable avec le slug " + slug)
        );
    }

    /**
     * Method to add a Moto to database
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return MotoModel
     */
    public MotoModel addMoto(final MotoRequest motoRequest) {
        MotoModel newMoto = new MotoModel();

        Slugify slug = new Slugify();
        newMoto.setSlugMoto(slug.slugify(motoRequest.getNomMoto()));

        if (this.motoRepository.findMotoBySlug(newMoto.getSlugMoto()).isPresent()) {
            throw new MBDreamException("Une moto avec le slug " + newMoto.getSlugMoto() + " existe deja");
        }

        newMoto.setNomMoto(motoRequest.getNomMoto());
        newMoto.setDateAjout(Instant.now());
        newMoto.setDescriptionMoto(motoRequest.getDescriptionMoto());
        newMoto.setFeatured(motoRequest.isFeatured());

        final MarqueModel marque = this.marqueRepository.findMarqueBySlug(motoRequest.getSlugMarque()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque avec le slug " + motoRequest.getSlugMarque())
        );

        final CategorieModel categorie = this.categorieRepository.findCategorieBySlug(motoRequest.getSlugCategorie()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la categorie avec le slug " + motoRequest.getSlugCategorie())
        );

        newMoto.setMarque(marque);
        newMoto.setCategorie(categorie);

        newMoto = this.motoRepository.save(newMoto);

        motoRequest.setSlugMoto(newMoto.getSlugMoto());

        return newMoto;
    }

    /**
     * Method to update data of a Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return updatedmoto
     */
    public MotoModel updateMoto(final MotoRequest motoRequest) {
        MotoModel updatedMoto = this.findMotoBySlug(motoRequest.getSlugMoto());

        // Update tout car le formulaire aura de base toutes les infos et les envois
        updatedMoto.setDescriptionMoto(motoRequest.getDescriptionMoto());

        if (!updatedMoto.getMarque().getSlugMarque().equals(motoRequest.getSlugMarque())) {
            final MarqueModel marque = this.marqueRepository.findMarqueBySlug(motoRequest.getSlugMarque()).orElseThrow(
                    () -> new MBDreamException("Impossible de trouver la marque avec le slug " + motoRequest.getSlugMarque())
            );
            updatedMoto.setMarque(marque);
        }

        if (!updatedMoto.getCategorie().getSlugCategorie().equals(motoRequest.getSlugCategorie())) {
            final CategorieModel categorie = this.categorieRepository.findCategorieBySlug(motoRequest.getSlugCategorie()).orElseThrow(
                    () -> new MBDreamException("Impossible de trouver la categorie avec le slug " + motoRequest.getSlugCategorie())
            );
            updatedMoto.setCategorie(categorie);
        }

        return this.motoRepository.save(updatedMoto);
    }

    /**
     * Method to delete a Moto
     *
     * @param slug Slug of moto to delete
     */
    public void deleteMoto(final String slug) {
        MotoModel moto = this.findMotoBySlug(slug);

        for (MediaModel media : moto.getMedias()) {
            this.mediaRepository.delete(media);
        }

        this.motoRepository.deleteById(moto.getIdMoto());
    }
}
