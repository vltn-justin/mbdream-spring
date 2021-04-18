package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.MotoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository for Moto
 *
 * @author Chamalo
 */
@Repository
public interface MotoRepository extends CrudRepository<MotoModel, Long> {
    @Query("SELECT m FROM Moto m WHERE m.isFeatured = true")
    Collection<MotoModel> findAllFeaturedMoto();

    @Query("SELECT m FROM Moto m WHERE m.slugMoto = :slug")
    Optional<MotoModel> findMotoBySlug(final String slug);

    @Query(nativeQuery = true, value = "SELECT * FROM moto m LIMIT 10 OFFSET :page")
    Iterable<MotoModel> getMotoByPage(final Integer page);
}
