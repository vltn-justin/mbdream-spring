package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.DTO.CategorieRequest;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.responses.CategorieResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Rest Controller for Categorie
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app"}) // Authorize angular
@RestController
@RequestMapping("/category")
public class CategorieController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieController(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * Method to add a Category to database
     *
     * @param categorieRequest CategorieRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategorie(@RequestBody final CategorieRequest categorieRequest) {
        if (this.categorieService.addCategorie(categorieRequest).getIdCategorie() != null) {
            return ResponseEntity.ok("Catégorie ajoutée");
        }
        return ResponseEntity.ok("Impossible d'ajouter la catégorie, essayez à nouveau");
    }

    /**
     * Method to get a category with is id, mapped at /category/get/id/
     *
     * @param id Id of Category
     *
     * @return Category or MBDreamException
     */
    @GetMapping("/get/id/{id}")
    public ResponseEntity<Map<String, Object>> findCategorieById(@PathVariable final String id) {
        CategorieModel categorie = this.categorieService.findCategorieById(id);

        if (categorie != null) {
            return ResponseEntity.ok(CategorieResponse.buildResponse(ResponseType.BASIC, categorie));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to get a category with is slug, mapped at /category/get/slug/
     *
     * @param slug Slug of Category
     *
     * @return Category or MBDreamException
     */
    @GetMapping("/get/slug/{slug}")
    public ResponseEntity<Map<String, Object>> findCategorieBySlug(@PathVariable final String slug) {
        CategorieModel categorie = this.categorieService.findCategorieBySlug(slug);

        if (categorie != null) {
            return ResponseEntity.ok(CategorieResponse.buildResponse(ResponseType.BASIC, categorie));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to find all Categories
     *
     * @return Iterable of CategorieModel
     */
    @GetMapping("/get")
    public ResponseEntity<Iterable<CategorieModel>> findAllCategorie() {
        return ResponseEntity.ok(this.categorieService.findAllCategorie());
    }

    /**
     * Method to delete a categorie with is id
     *
     * @param id Id of categorie to delete
     *
     * @return ResponseEntity
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable final String id) {
        this.categorieService.deleteCategorie(id);
        return ResponseEntity.ok("Catégorie supprimée");
    }
}
