package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.MarqueDTO;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.responses.MarqueResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MarqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Marque
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200", "https://motorbike-dream.web.app"})
@RestController
@RequestMapping("/marques")
public class MarqueController {

    private final MarqueService marqueService;

    @Autowired
    public MarqueController(final MarqueService marqueService) {
        this.marqueService = marqueService;
    }

    /**
     * Method to add a marque to database
     *
     * @param marqueDTO MarqueRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity<String> addMarque(@RequestBody final MarqueDTO marqueDTO) {
        final MarqueModel newMarque = this.marqueService.addMarque(marqueDTO);
        if (newMarque.getIdMarque() != null) {
            return ResponseEntity.ok("Marque ajoutée - " + newMarque.getSlugMarque());
        }
        return ResponseEntity.ok("Impossible d'ajouter la marque, essayez à nouveau");
    }

    /**
     * Method to update a Marque
     *
     * @param slug      Slug of Marque
     * @param marqueDTO MarqueRequest with all data
     *
     * @return ReponseEntity
     */
    @PutMapping("/{slug}")
    public ResponseEntity<String> updateMarque(@PathVariable final String slug,
                                               @RequestBody final MarqueDTO marqueDTO) {
        marqueDTO.setSlugMarque(slug);

        if (this.marqueService.updateMarque(marqueDTO) != null) {
            return ResponseEntity.ok("Marque mise à jour");
        }

        return ResponseEntity.ok("Impossible de mettre à jour la marque, essayez à nouveau");
    }

    /**
     * Method to get a marque with is id, mapped at /marque/get/slug/
     *
     * @param slugMarque Slug of marque
     *
     * @return Marque or MBDreamException
     */
    @GetMapping("/{slugMarque}")
    public ResponseEntity<Object> findMarqueBySlug(@PathVariable final String slugMarque) {
        MarqueModel marque = this.marqueService.findMarqueBySlug(slugMarque);

        if (marque != null) {
            return ResponseEntity.ok(new MarqueResponse().buildResponse(ResponseType.BASIC, marque));
        }

        return ResponseEntity.status(404).body("Marque introuvable");
    }

    /**
     * Method to get all marques, limited by 10 per page
     *
     * @return Response Entity
     */
    @GetMapping("")
    private ResponseEntity<Object> findAllByPage(
            @RequestParam(defaultValue = "0", required = false) final Integer page) {
        Iterable<MarqueModel> allMarque = this.marqueService.findAllMarqueByPage(page);

        List<Map<String, Object>> mapList = new ArrayList<>();

        for (MarqueModel marque : allMarque) {
            mapList.add(new MarqueResponse().buildResponse(ResponseType.INFO, marque));
        }

        return ResponseEntity.ok(mapList);
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
     * @param slugMarque Slug of Marque
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/{slugMarque}")
    public ResponseEntity<String> deleteMarque(@PathVariable final String slugMarque) {
        this.marqueService.deleteMarque(slugMarque);
        return ResponseEntity.ok("Marque supprimé");
    }
}
