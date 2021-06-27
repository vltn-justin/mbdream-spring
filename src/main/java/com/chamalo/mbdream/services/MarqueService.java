package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.MarqueDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MarqueRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Marque
 *
 * @author Chamalo
 */
@Service
public class MarqueService {

    private final MarqueRepository marqueRepository;

    private final MotoService motoService;

    @Autowired
    public MarqueService(final MarqueRepository marqueRepository, final MotoService motoService) {
        this.marqueRepository = marqueRepository;
        this.motoService = motoService;
    }

    /**
     * Method to add a marque to database
     *
     * @param marqueDTO MarqueRequest with all data
     *
     * @return ResponseEntity
     */
    public MarqueModel addMarque(final MarqueDTO marqueDTO) {
        final var newMarque = new MarqueModel();

        newMarque.setNomMarque(marqueDTO.getNomMarque());
        newMarque.setDescriptionMarque(marqueDTO.getDescriptionMarque());
        newMarque.setLogoMarque(marqueDTO.getLogoMarque());
        newMarque.setDateCreation(marqueDTO.getDateCreation());

        final var slug = new Slugify();
        newMarque.setSlugMarque(slug.slugify(marqueDTO.getNomMarque()));

        return this.marqueRepository.save(newMarque);
    }

    /**
     * Method to get a marque with is slug
     *
     * @param slug Slug of marque
     *
     * @return MarqueModel if found, MBDreamException otherwise
     */
    public MarqueModel findMarqueBySlug(final String slug) {
        return this.marqueRepository.findMarqueBySlug(slug)
                .orElseThrow(() -> new MBDreamException("Marque introuvable avec le slug " + slug));
    }

    /**
     * Method to get all marques, limited by 10
     *
     * @param page Page number
     *
     * @return Iterable of MarqueModel
     */
    public Iterable<MarqueModel> findAllMarqueByPage(final Integer page) {
        return this.marqueRepository.getMarqueByPage(page * 10);
    }

    /**
     * Method to count all marques inside database
     *
     * @return Long
     */
    public Long countAllMarque() {
        return this.marqueRepository.count();
    }

    /**
     * Method to update data of a Marque
     *
     * @param marqueDTO MarqueRequest with all data
     *
     * @return updatedMarque
     */
    public MarqueModel updateMarque(final MarqueDTO marqueDTO) {
        MarqueModel updatedMarque = this.findMarqueBySlug(marqueDTO.getSlugMarque());

        updatedMarque.setNomMarque(marqueDTO.getNomMarque());
        updatedMarque.setDescriptionMarque(marqueDTO.getDescriptionMarque());
        updatedMarque.setLogoMarque(marqueDTO.getLogoMarque());
        updatedMarque.setDateCreation(marqueDTO.getDateCreation());

        return this.marqueRepository.save(updatedMarque);
    }

    /**
     * Method to delete marque
     *
     * @param slug Slug of marque to delete
     */
    public void deleteMarque(final String slug) {
        final MarqueModel marque = this.findMarqueBySlug(slug);

        for (MotoModel moto : marque.getMotos()) {
            motoService.deleteMoto(moto.getIdMoto().toString());
        }

        this.marqueRepository.delete(marque);
    }
}
