package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MarqueRequest;
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
    public MarqueService (final MarqueRepository marqueRepository, final MotoService motoService) {
        this.marqueRepository = marqueRepository;
        this.motoService = motoService;
    }

    /**
     * Method to add a marque to database
     *
     * @param marqueRequest MarqueRequest with all data
     *
     * @return ResponseEntity
     */
    public MarqueModel addMarque (final MarqueRequest marqueRequest) {
        MarqueModel newMarque = new MarqueModel();

        newMarque.setNomMarque(marqueRequest.getNomMarque());
        newMarque.setDescriptionMarque(marqueRequest.getDescriptionMarque());
        newMarque.setLogoMarque(marqueRequest.getLogoMarque());
        newMarque.setDateCreation(marqueRequest.getDateCreation());

        Slugify slug = new Slugify();
        newMarque.setSlugMarque(slug.slugify(marqueRequest.getNomMarque()));

        return this.marqueRepository.save(newMarque);
    }

    /**
     * Method to get a marque with is id
     *
     * @param id Id of marque
     *
     * @return MarqueModel if found, MBDreamException otherwise
     */
    public MarqueModel findMarqueById (final String id) {
        return this.marqueRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new MBDreamException("Marque introuvable avec l'id " + id)
        );
    }

    /**
     * Method to get a marque with is slug
     *
     * @param slug Slug of marque
     *
     * @return MarqueModel if found, MBDreamException otherwise
     */
    public MarqueModel findMarqueBySlug(final String slug) {
        return this.marqueRepository.findMarqueBySlug(slug).orElseThrow(
                () -> new MBDreamException("Marque introuvable avec le slug " + slug)
        );
    }

    /**
     * Method to get all marques
     *
     * @return Iterable of MarqueModel
     */
    public Iterable<MarqueModel> findAllMarque () {
        return this.marqueRepository.findAll();
    }

    /**
     * Method to count all marques inside database
     *
     * @return Long
     */
    public Long countAllMarque () {
        return this.marqueRepository.count();
    }

    /**
     * Method to update data of a Marque
     *
     * @param marqueRequest MarqueRequest with all data
     *
     * @return updatedMarque
     */
    public MarqueModel updateMarque (final MarqueRequest marqueRequest) {
        MarqueModel updatedMarque = this.findMarqueById(marqueRequest.getIdMarque().toString());

        updatedMarque.setNomMarque(marqueRequest.getNomMarque());
        updatedMarque.setDescriptionMarque(marqueRequest.getDescriptionMarque());
        updatedMarque.setLogoMarque(marqueRequest.getLogoMarque());
        updatedMarque.setDateCreation(marqueRequest.getDateCreation());

        return this.marqueRepository.save(updatedMarque);
    }

    /**
     * Method to delete marque
     *
     * @param id Id of marque to delete
     */
    public void deleteMarque (final String id) {
        final MarqueModel marque = this.findMarqueById(id);

        for (MotoModel moto : marque.getMotos()) {
            motoService.deleteMoto(moto.getIdMoto().toString());
        }

        this.marqueRepository.delete(marque);
    }
}
