package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.MediaModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Media
 *
 * @author Chamalo
 */
@Repository
public interface MediaRepository extends CrudRepository<MediaModel, Long> {
    @Query("SELECT m FROM Media m WHERE m.moto.slugMoto = :slugMoto AND m.isVideo = :isVideo")
    Optional<List<MediaModel>> findAllMediaOfMoto(final String slugMoto, final Boolean isVideo);
}
