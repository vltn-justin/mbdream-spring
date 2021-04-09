package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.DTO.MarqueRequest;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.responses.MarqueResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Rest Controller for Marque
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app"}) // Authorize angular
@RestController
@RequestMapping("/marque")
public class MarqueController {

    private final MarqueService marqueService;

    @Autowired
    public MarqueController(final MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    /**
     * Method to add a marque to database
     *
     * @param marqueRequest MarqueRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-marque")
    public ResponseEntity<String> addMarque(@RequestBody final MarqueRequest marqueRequest) {
        if (this.marqueService.addMarque(marqueRequest).getIdMarque() != null) {
            return ResponseEntity.ok("Marque ajoutée");
        }
        return ResponseEntity.ok("Impossible d'ajouter la marque, essayez à nouveau");
    }

    /**
     * Method to update a Marque
     *
     * @param marqueRequest MarqueRequest with all data
     *
     * @return ReponseEntity
     */
    @PostMapping("/update-marque")
    public ResponseEntity<String> updateMarque(@RequestBody final MarqueRequest marqueRequest) {
        if (this.marqueService.updateMarque(marqueRequest) != null) {
            return ResponseEntity.ok("Marque mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour la marque, essayez à nouveau");
    }

    /**
     * Method to get a marque with is id, mapped at /marque/get/id/
     *
     * @param id Id of marque
     *
     * @return Marque or MBDreamException
     */
    @GetMapping("/get/id/{id}")
    public ResponseEntity<Map<String, Object>> findMarqueById(@PathVariable final String id) {
        MarqueModel marque = this.marqueService.findMarqueById(id);

        if (marque != null) {
            return ResponseEntity.ok(MarqueResponse.buildResponse(ResponseType.BASIC, marque));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to get a marque with is id, mapped at /marque/get/slug/
     *
     * @param slug Slug of marque
     *
     * @return Marque or MBDreamException
     */
    @GetMapping("/get/slug/{slug}")
    public ResponseEntity<Map<String, Object>> findMarqueBySlug(@PathVariable final String slug) {
        MarqueModel marque = this.marqueService.findMarqueBySlug(slug);

        if (marque != null) {
            return ResponseEntity.ok(MarqueResponse.buildResponse(ResponseType.BASIC, marque));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to get all marques
     *
     * @return Iterable of MarqueModel
     */
    @GetMapping("/get")
    public ResponseEntity<Iterable<MarqueModel>> findAllMarque() {
        return ResponseEntity.ok(this.marqueService.findAllMarque());
    }

    /**
     * Method to count all marques inside database
     *
     * @return ResponseEntity<Long>
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAllMarque() {
        return ResponseEntity.ok(this.marqueService.countAllMarque());
    }

    /**
     * Method to delete a marque with is id
     *
     * @param id Id of Marque
     *
     * @return ResponseEntity
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteMarque(@PathVariable final String id) {
        this.marqueService.deleteMarque(id);
        return ResponseEntity.ok("Marque supprimé");
    }
}
