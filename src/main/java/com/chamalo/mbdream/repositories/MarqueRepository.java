package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.MarqueModel;
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

    @Query(nativeQuery = true, value = "SELECT * FROM marque m LIMIT 10 OFFSET :page")
    Iterable<MarqueModel> getMarqueByPage(final Integer page);
}
