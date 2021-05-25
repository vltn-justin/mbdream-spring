package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.MotoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.responses.MotoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MotoService;
import com.chamalo.mbdream.utils.PasswordEncoderJava;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Moto
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app"})
@RestController
@RequestMapping("/moto")
public class MotoController {
    private static final Logger      LOGGER = LoggerFactory.getLogger(MotoController.class);

    private final        MotoService motoService;

    @Autowired
    public MotoController(final MotoService motoService) {
        this.motoService = motoService;
    }

    /**
     * Method to add a Moto to database
     *
     * @param motoDTO MotoRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<String> addMoto(@RequestBody final MotoDTO motoDTO) {
        MotoModel moto = this.motoService.addMoto(motoDTO);
        if (moto.getIdMoto() != null) {
            return ResponseEntity.ok("Moto ajoutée - " + moto.getSlugMoto());
        }
        return ResponseEntity.ok("Impossible d'ajouter la moto, essayez à nouveau");
    }

    /**
     * Method to update data of a Moto
     *
     * @param motoDTO MotoRequest with all data
     *
     * @return null if moto is not find, updatedmoto otherwise
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateMoto(@RequestBody final MotoDTO motoDTO) {
        if (this.motoService.updateMoto(motoDTO) != null) {
            return ResponseEntity.ok("Moto mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour la moto, essayez à nouveau");
    }

    /**
     * Method to find a Moto with is slug
     *
     * @param slug Slug
     *
     * @return ResponseEntity of MotoModel
     */
    @GetMapping(value = "/get/{slug}")
    public ResponseEntity<Object> findMotoBySlug(@PathVariable final String slug) {
        MotoModel moto;
        try {
            moto = this.motoService.findMotoBySlug(slug);
        } catch (final MBDreamException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

        return ResponseEntity.ok(new MotoResponse().buildResponse(ResponseType.BASIC, moto));
    }

    /**
     * Method to get all moto, limited by 10 per page
     *
     * @return Response Entity
     */
    @GetMapping(value = "/get/page/{page}")
    public ResponseEntity<Object> findAllByPage(@PathVariable final Integer page) {
        try {
            final Collection<MotoModel> allMoto = this.motoService.findMotoByPage(page);

            final Map<String, Object> mapResponse = new HashMap<>();
            final Long count = this.countAllMoto().getBody();
            mapResponse.put("count", count);
            mapResponse.put("haveNext", count / 10 < page);

            final List<Map<String, Object>> mapList = new ArrayList<>();

            for (final MotoModel moto : allMoto) {
                mapList.add(new MotoResponse().buildResponse(ResponseType.LIGHT, moto));
            }

            mapResponse.put("results", mapList);

            return ResponseEntity.ok(mapResponse);
        } catch (final MBDreamException e) {
            LOGGER.info(e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Method to count all motos inside database
     *
     * @return ResponseEntity<Long>
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAllMoto() {
        return ResponseEntity.ok(this.motoService.countAllMoto());
    }

    /**
     * Method to get all moto featured
     *
     * @return ResponseEntity Iterable of MotoModel
     */
    @GetMapping("/featured")
    public ResponseEntity<Object> findAllFeaturedMoto() {
        try {
            return ResponseEntity.ok(this.motoService.findFeaturedMoto());
        } catch (final MBDreamException e) {
            LOGGER.info(e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Method to delete a moto with is slug
     *
     * @param slug Slug of moto
     *
     * @return ResponseEntity
     */
    @GetMapping("/delete/{slug}")
    public ResponseEntity<String> deleteMoto(@PathVariable final String slug) {
        this.motoService.deleteMoto(slug);

        return ResponseEntity.ok("Moto supprimée");
    }
}
