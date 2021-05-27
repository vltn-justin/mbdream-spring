package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.MotoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MotoRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class MotoService {

    private final MotoRepository motoRepository;

    private final MarqueService marqueService;
    private final CategorieService categorieService;

    // @Lazy pour eviter une boucle dans les dépendances (MotoService -> MarqueService -> MotoService -> ...)
    @Autowired
    public MotoService(final MotoRepository motoRepository,
            @Lazy final MarqueService marqueService,
            @Lazy final CategorieService categorieService) {
        this.motoRepository = motoRepository;
        this.marqueService = marqueService;
        this.categorieService = categorieService;
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
        var newMoto = new MotoModel();

        final var slug = new Slugify();
        newMoto.setSlugMoto(slug.slugify(motoDTO.getNomMoto()));

        if (this.motoRepository.findMotoBySlug(newMoto.getSlugMoto()).isPresent()) {
            throw new MBDreamException("Une moto avec le slug " + newMoto.getSlugMoto() + " existe deja");
        }

        newMoto.setNomMoto(motoDTO.getNomMoto());
        newMoto.setDateAjout(Instant.now());
        newMoto.setDescriptionMoto(motoDTO.getDescriptionMoto());
        newMoto.setFeatured(motoDTO.isFeatured());
        newMoto.setMarque(this.marqueService.findMarqueBySlug(motoDTO.getSlugMarque()));
        newMoto.setCategorie(this.categorieService.findCategorieBySlug(motoDTO.getSlugCategorie()));

        newMoto = this.motoRepository.save(newMoto);

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
        // On va pouvoir seulement mettre à jour la description, la marque et la catégorie
        MotoModel updatedMoto = this.findMotoBySlug(motoDTO.getSlugMoto());

        // Si la description change
        if (!updatedMoto.getDescriptionMoto().equals(motoDTO.getDescriptionMoto())) {
            updatedMoto.setDescriptionMoto(motoDTO.getDescriptionMoto());
        }

        // Si la marque change
        if (updatedMoto.getMarque() == null || !updatedMoto.getMarque().getSlugMarque().equals(motoDTO.getSlugMarque())) {
            updatedMoto.setMarque(this.marqueService.findMarqueBySlug(motoDTO.getSlugMarque()));
        }

        // Si la catégorie change
        if (updatedMoto.getCategorie() == null || !updatedMoto.getCategorie().getSlugCategorie().equals(motoDTO.getSlugCategorie())) {
            updatedMoto.setCategorie(this.categorieService.findCategorieBySlug(motoDTO.getSlugCategorie()));
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
