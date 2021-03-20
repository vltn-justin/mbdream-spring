package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.CategorieModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Categorie
 *
 * @author Chamalo
 */
@Repository
public interface CategorieRepository extends CrudRepository<CategorieModel, Long> {
    @Query("SELECT c FROM Categorie c WHERE c.slugCategorie = :slug")
    Optional<CategorieModel> findCategorieBySlug(final String slug);
}
