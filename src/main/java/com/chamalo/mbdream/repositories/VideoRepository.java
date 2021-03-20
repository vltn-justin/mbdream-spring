package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.VideoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Video
 *
 * @author Chamalo
 */
@Repository
public interface VideoRepository extends CrudRepository<VideoModel, Long> {
    @Query("SELECT v FROM Video v WHERE v.moto.idMoto = :idMoto")
    Optional<List<VideoModel>> findAllVideoOfMoto(final Long idMoto);
}
