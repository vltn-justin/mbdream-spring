package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MotoRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.*;
import com.chamalo.mbdream.repositories.*;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
     * Method to get all moto
     *
     * @return Iterable of MotoModel
     */
    public Iterable<MotoModel> findAllMoto() {
        return this.motoRepository.findAll();
    }

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
     * Method to get a moto with is id, mapped at /moto/id
     *
     * @param id Id of Moto
     *
     * @return Moto or MBDreamException
     */
    public MotoModel findMotoById(final String id) {
        return this.motoRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new MBDreamException("Moto introuvable avec l'id " + id)
        );
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

        newMoto.setNomMoto(motoRequest.getNomMoto());
        newMoto.setPrixMoto(motoRequest.getPrixMoto());
        newMoto.setDateAjout(Instant.now());
        newMoto.setDescriptionMoto(motoRequest.getDescriptionMoto());
        newMoto.setFeatured(motoRequest.isFeatured());

        newMoto = this.motoRepository.save(newMoto);

        if (motoRequest.getMarqueId() != null) {
            newMoto = this.addMarque(motoRequest);
        }

        if (motoRequest.getCategorieId() != null) {
            newMoto = this.addCategory(motoRequest);
        }

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
        MotoModel updatedMoto = this.findMotoById(motoRequest.getIdMoto().toString());

        // Update tout car le formulaire aura de base toutes les infos et les envois
        updatedMoto.setDescriptionMoto(motoRequest.getDescriptionMoto());
        updatedMoto.setPrixMoto(motoRequest.getPrixMoto());

        if (updatedMoto.getMarque() == null || !updatedMoto.getMarque().getIdMarque().equals(motoRequest.getMarqueId())) {
            this.deleteMarque(motoRequest);
            updatedMoto = this.addMarque(motoRequest);
        }

        if (updatedMoto.getCategorie() == null || !updatedMoto.getCategorie().getIdCategorie().equals(motoRequest.getCategorieId())) {
            this.deleteCategory(motoRequest);
            updatedMoto = this.addCategory(motoRequest);
        }

        return this.motoRepository.save(updatedMoto);
    }

    /**
     * Method to delete a Moto
     *
     * @param id ID of moto to delete
     */
    public void deleteMoto(final String id) {
        MotoModel moto = this.findMotoById(id);

        for (MediaModel media : moto.getMedias()) {
            this.mediaRepository.delete(media);
        }

        this.motoRepository.deleteById(Long.parseLong(id));
    }

    /**
     * Method to add Marque to Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return Updated moto
     */
    public MotoModel addMarque(final MotoRequest motoRequest) {
        MarqueModel marque = this.marqueRepository.findById(motoRequest.getMarqueId()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque")
        );

        MotoModel moto = this.findMotoById(motoRequest.getIdMoto().toString());

        marque.addMoto(moto);

        marque = this.marqueRepository.save(marque);

        moto.setMarque(marque);

        return this.motoRepository.save(moto);
    }

    /**
     * Method to delete Marque to Moto
     *
     * @param motoRequest MotoRequest with all data
     */
    private void deleteMarque(final MotoRequest motoRequest) {
        MarqueModel marque = this.marqueRepository.findById(motoRequest.getMarqueId()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque")
        );

        MotoModel moto = this.findMotoById(motoRequest.getIdMoto().toString());

        marque.removeMoto(moto);
        this.marqueRepository.save(marque);

        moto.setMarque(null);

        this.motoRepository.save(moto);
    }

    /**
     * Method to add Category to Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return Updated moto
     */
    public MotoModel addCategory(final MotoRequest motoRequest) {
        CategorieModel categorie = this.categorieRepository.findById(motoRequest.getCategorieId()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque")
        );

        MotoModel moto = this.findMotoById(motoRequest.getIdMoto().toString());

        categorie.addMoto(moto);

        categorie = this.categorieRepository.save(categorie);

        moto.setCategorie(categorie);

        return this.motoRepository.save(moto);
    }

    /**
     * Method to delete Category to Moto
     *
     * @param motoRequest MotoRequest with all data
     */
    private void deleteCategory(final MotoRequest motoRequest) {
        CategorieModel categorie = this.categorieRepository.findById(motoRequest.getCategorieId()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque")
        );

        MotoModel moto = this.findMotoById(motoRequest.getIdMoto().toString());

        categorie.addMoto(moto);

        this.categorieRepository.save(categorie);

        moto.setCategorie(null);

        this.motoRepository.save(moto);
    }
}
