package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.CategorieDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.CategorieRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;
    private final MotoRepository motoRepository;

    @Autowired
    public CategorieService(final CategorieRepository categorieRepository, final MotoRepository motoRepository) {
        this.categorieRepository = categorieRepository;
        this.motoRepository = motoRepository;
    }

    /**
     * Method to add a Category to database
     *
     * @param categorieDTO CategorieRequest with all data
     *
     * @return ResponseEntity
     */
    public CategorieModel addCategorie(final CategorieDTO categorieDTO) {
        final var newCategorie = new CategorieModel();

        newCategorie.setNomCategorie(categorieDTO.getNomCategorie());

        final var slug = new Slugify();
        newCategorie.setSlugCategorie(slug.slugify(categorieDTO.getNomCategorie()));

        return this.categorieRepository.save(newCategorie);
    }

    /**
     * Method to get a category with is slug
     *
     * @param slug Slug of Category
     *
     * @return Category or MBDreamException
     */
    public CategorieModel findCategorieBySlug(final String slug) {
        return this.categorieRepository.findCategorieBySlug(slug).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la catégorie avec le slug " + slug)
        );
    }

    /**
     * Method to find all Categories
     *
     * @return Iterable of CategorieModel
     */
    public Iterable<CategorieModel> findAllCategorie() {
        return this.categorieRepository.findAll();
    }

    /**
     * Method to delete a Category with is slug
     *
     * @param slug Slug of Category
     */
    public void deleteCategorie(final String slug) {
        final var categorie = this.findCategorieBySlug(slug);

        for (final var moto : categorie.getMotos()) {
            moto.setCategorie(null);
            this.motoRepository.save(moto);
        }

        this.categorieRepository.delete(categorie);
    }
}
