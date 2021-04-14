package com.chamalo.mbdream.repositories;

import com.chamalo.mbdream.models.ImageModel;
import com.chamalo.mbdream.models.InfoModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Info
 *
 * @author Chamalo
 */
@Repository
public interface InfoRepository extends CrudRepository<InfoModel, Long> {
    @Query("SELECT i FROM Info i WHERE i.moto.slugMoto = :slugMoto")
    Optional<InfoModel> findInfoMoto(final String slugMoto);
}
