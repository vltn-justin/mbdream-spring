package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.CategorieDTO;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.responses.CategorieResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
     * @param categorieDTO CategorieRequest with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<String> addCategorie(@RequestBody final CategorieDTO categorieDTO) {
        if (this.categorieService.addCategorie(categorieDTO).getIdCategorie() != null) {
            return ResponseEntity.ok("Catégorie ajoutée");
        }
        return ResponseEntity.ok("Impossible d'ajouter la catégorie, essayez à nouveau");
    }

    /**
     * Method to get a category with is slug, mapped at /category/get/slug/
     *
     * @param slug Slug of Category
     *
     * @return Category or MBDreamException
     */
    @GetMapping("/get/{slug}")
    public ResponseEntity<Map<String, Object>> findCategorieBySlug(@PathVariable final String slug) {
        CategorieModel categorie = this.categorieService.findCategorieBySlug(slug);

        if (categorie != null) {
            return ResponseEntity.ok(new CategorieResponse().buildResponse(ResponseType.BASIC, categorie));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to find all Categories
     *
     * @return Iterable of CategorieModel
     */
    @GetMapping("/get")
    public ResponseEntity<List<Map<String, Object>>> findAllCategorie() {
        Iterable<CategorieModel> allCategorie = this.categorieService.findAllCategorie();

        List<Map<String, Object>> mapList = new ArrayList<>();

        for (CategorieModel categorie : allCategorie) {
            mapList.add(new CategorieResponse().buildResponse(ResponseType.LIGHT, categorie));
        }

        return ResponseEntity.ok(mapList);
    }

    /**
     * Method to delete a categorie with is id
     *
     * @param slug Slug of categorie to delete
     *
     * @return ResponseEntity
     */
    @GetMapping("/delete/{slug}")
    public ResponseEntity<String> deleteCategorie(@PathVariable final String slug) {
        this.categorieService.deleteCategorie(slug);
        return ResponseEntity.ok("Catégorie supprimée");
    }
}
