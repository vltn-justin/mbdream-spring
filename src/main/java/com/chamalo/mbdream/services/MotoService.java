package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.MotoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.CategorieRepository;
import com.chamalo.mbdream.repositories.MarqueRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
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

    @Autowired
    public MotoService(final MotoRepository motoRepository,
                       final MarqueRepository marqueRepository,
                       final CategorieRepository categorieRepository) {
        this.motoRepository = motoRepository;
        this.marqueRepository = marqueRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Method to get all moto, limited by 10 <br>
     * We multiply by 10, because user will put 1, 2, 3, ...
     *
     * @param page Page number
     *
     * @return Collection of MotoModel
     */
    public Collection<MotoModel> findMotoByPage(final Integer page) {
        final Collection<MotoModel> motoModels = this.motoRepository.findMotoByPage(page * 10);

        if (!motoModels.isEmpty()) {
            return motoModels;
        }

        throw new MBDreamException("Aucune motos trouvée !");
    }

    /**
     * Method to get all moto featured
     *
     * @return Iterable of MotoModel
     */
    public Collection<MotoModel> findFeaturedMoto() {
        final Collection<MotoModel> motoModels = this.motoRepository.findFeaturedMoto();

        if (!motoModels.isEmpty()) {
            return motoModels;
        }

        throw new MBDreamException("Aucune featured moto trouvée");
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
     * @param motoDTO MotoRequest with all data
     *
     * @return MotoModel
     */
    public MotoModel addMoto(final MotoDTO motoDTO) {
        MotoModel newMoto = new MotoModel();

        Slugify slug = new Slugify();
        newMoto.setSlugMoto(slug.slugify(motoDTO.getNomMoto()));

        if (this.motoRepository.findMotoBySlug(newMoto.getSlugMoto()).isPresent()) {
            throw new MBDreamException("Une moto avec le slug " + newMoto.getSlugMoto() + " existe deja");
        }

        newMoto.setNomMoto(motoDTO.getNomMoto());
        newMoto.setDateAjout(Instant.now());
        newMoto.setDescriptionMoto(motoDTO.getDescriptionMoto());
        newMoto.setFeatured(motoDTO.isFeatured());

        final MarqueModel marque = this.marqueRepository.findMarqueBySlug(motoDTO.getSlugMarque()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la marque avec le slug " + motoDTO.getSlugMarque())
        );

        final CategorieModel categorie = this.categorieRepository.findCategorieBySlug(motoDTO.getSlugCategorie()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la categorie avec le slug " + motoDTO.getSlugCategorie())
        );

        newMoto.setMarque(marque);
        newMoto.setCategorie(categorie);

        newMoto = this.motoRepository.save(newMoto);

        motoDTO.setSlugMoto(newMoto.getSlugMoto());

        return newMoto;
    }

    /**
     * Method to update data of a Moto
     *
     * @param motoDTO MotoRequest with all data
     *
     * @return updatedmoto
     */
    public MotoModel updateMoto(final MotoDTO motoDTO) {
        MotoModel updatedMoto = this.findMotoBySlug(motoDTO.getSlugMoto());

        // Update tout car le formulaire aura de base toutes les infos et les envois
        updatedMoto.setDescriptionMoto(motoDTO.getDescriptionMoto());

        // Si la marque change
        if (!updatedMoto.getMarque().getSlugMarque().equals(motoDTO.getSlugMarque())) {
            final MarqueModel marque = this.marqueRepository.findMarqueBySlug(motoDTO.getSlugMarque()).orElseThrow(
                    () -> new MBDreamException("Impossible de trouver la marque avec le slug " + motoDTO.getSlugMarque())
            );
            updatedMoto.setMarque(marque);
        }

        // Si la catégorie change
        if (!updatedMoto.getCategorie().getSlugCategorie().equals(motoDTO.getSlugCategorie())) {
            final CategorieModel categorie = this.categorieRepository.findCategorieBySlug(motoDTO.getSlugCategorie()).orElseThrow(
                    () -> new MBDreamException("Impossible de trouver la categorie avec le slug " + motoDTO.getSlugCategorie())
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
        this.motoRepository.delete(moto);
    }
}
