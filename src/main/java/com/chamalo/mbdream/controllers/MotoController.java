package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.DTO.MotoRequest;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.responses.MotoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Moto
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app"}) // Authorize angular
@RestController
@RequestMapping("/moto")
public class MotoController {
    private final MotoService motoService;

    @Autowired
    public MotoController(final MotoService motoService) {
        this.motoService = motoService;
    }

    /**
     * Method to add a Moto to database
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-moto")
    public ResponseEntity<String> addMoto(@RequestBody final MotoRequest motoRequest) {
        if (this.motoService.addMoto(motoRequest).getIdMoto() != null) {
            return ResponseEntity.ok("Moto ajoutée");
        }
        return ResponseEntity.ok("Impossible d'ajouter la moto, essayez à nouveau");
    }

    /**
     * Method to update data of a Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return null if moto is not find, updatedmoto otherwise
     */
    @PostMapping("/update-moto")
    public ResponseEntity<String> updateMoto(@RequestBody final MotoRequest motoRequest) {
        if (this.motoService.updateMoto(motoRequest) != null) {
            return ResponseEntity.ok("Moto mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour la moto, essayez à nouveau");
    }

    /**
     * Method to get a moto with is id, mapped at /moto/id
     *
     * @param id Id of Moto
     *
     * @return Moto or MBDreamException
     */
    @GetMapping(value = "/get/id/{id}")
    public ResponseEntity<Map<String, Object>> findMotoById(@PathVariable final String id) {
        MotoModel moto = this.motoService.findMotoById(id);

        if (moto != null) {
            return ResponseEntity.ok(new MotoResponse().buildResponse(ResponseType.BASIC, moto));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Method to find a Moto with is slug
     *
     * @param slug Slug
     *
     * @return ResponseEntity of MotoModel
     */
    @GetMapping(value = "/get/slug/{slug}")
    public ResponseEntity<Map<String, Object>> findMotoBySlug(@PathVariable final String slug) {
        MotoModel moto = this.motoService.findMotoBySlug(slug);

        if (moto != null) {
            return ResponseEntity.ok(new MotoResponse().buildResponse(ResponseType.BASIC, moto));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Method to get all moto
     *
     * @return List of moto
     */
    @GetMapping(value = "/get")
    public ResponseEntity<List<Map<String, Object>>> findAll() {
        Iterable<MotoModel> allMoto = this.motoService.findAllMoto();

        List<Map<String, Object>> mapList = new ArrayList<>();

        for (MotoModel moto : allMoto) {
            mapList.add(new MotoResponse().buildResponse(ResponseType.LIGHT, moto));
        }

        return ResponseEntity.ok(mapList);
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
    public ResponseEntity<Iterable<MotoModel>> findAllFeaturedMoto() {
        return ResponseEntity.ok(this.motoService.findAllFeaturedMoto());
    }

    /**
     * Method to add a Marque to a Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-marque")
    public ResponseEntity<String> addMarque(@RequestBody final MotoRequest motoRequest) {
        if (this.motoService.addMarque(motoRequest) != null) {
            return ResponseEntity.ok("Marque ajoutée à la moto");
        }
        return ResponseEntity.ok("Impossible d'ajouter la marque à la moto, essayez à nouveau");
    }

    /**
     * Method to add a Category to a Moto
     *
     * @param motoRequest MotoRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategorie(@RequestBody final MotoRequest motoRequest) {
        if (this.motoService.addCategory(motoRequest) != null) {
            return ResponseEntity.ok("Categorie ajoutée à la moto");
        }
        return ResponseEntity.ok("Impossible d'ajouter la marque à la moto, essayez à nouveau");
    }

    /**
     * Method to delete a Moto
     *
     * @param id ID of moto to delete
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteMoto(@PathVariable final String id) {
        this.motoService.deleteMoto(id);
        return ResponseEntity.ok("Moto supprimée");
    }
}
