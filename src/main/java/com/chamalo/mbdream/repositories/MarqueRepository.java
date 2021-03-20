package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Marque
 *
 * @author Chamalo
 */
@Repository
public interface MarqueRepository extends CrudRepository<MarqueModel, Long> {
    @Query("SELECT m FROM Marque m WHERE m.slugMarque = :slug")
    Optional<MarqueModel> findMarqueBySlug(final String slug);
}
