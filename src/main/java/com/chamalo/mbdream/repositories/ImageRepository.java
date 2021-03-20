package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.ImageModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Image
 *
 * @author Chamalo
 */
@Repository
public interface ImageRepository extends CrudRepository<ImageModel, Long> {
    @Query("SELECT i FROM Image i WHERE i.moto.idMoto = :idMoto")
    Optional<List<ImageModel>> findAllImgOfMoto(final Long idMoto);
}
