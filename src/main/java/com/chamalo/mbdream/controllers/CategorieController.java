package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.CategorieDTO;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.responses.CategorieResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Categorie
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200", "https://motorbike-dream.web.app"})
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
	 * Method to find all Categories
	 *
	 * @return Iterable of CategorieModel
	 */
	@GetMapping("/get")
	public ResponseEntity<Object> findAllCategorie(@RequestParam(required = false, defaultValue = "") final String slug) {
		if (slug.isEmpty()) {
			Iterable<CategorieModel> allCategorie = this.categorieService.findAllCategorie();

			List<Map<String, Object>> mapList = new ArrayList<>();

			for (CategorieModel categorie : allCategorie) {
				mapList.add(new CategorieResponse().buildResponse(ResponseType.LIGHT, categorie));
			}

			return ResponseEntity.ok(mapList);
		} else {
			return this.findCategorieBySlug(slug);
		}

	}

	/**
	 * Method to get a category with is slug, mapped at /category/get/slug/
	 *
	 * @param slug Slug of Category
	 * @return Category or MBDreamException
	 */
	private ResponseEntity<Object> findCategorieBySlug(@PathVariable final String slug) {
		CategorieModel categorie = this.categorieService.findCategorieBySlug(slug);

		if (categorie != null) {
			return ResponseEntity.ok(new CategorieResponse().buildResponse(ResponseType.BASIC, categorie));
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Method to delete a categorie with is id
	 *
	 * @param slug Slug of categorie to delete
	 * @return ResponseEntity
	 */
	@GetMapping("/delete")
	public ResponseEntity<String> deleteCategorie(@RequestParam(required = false, defaultValue = "") final String slug) {
		if (slug.isEmpty()) {
			return ResponseEntity.status(404).body("Page introuvable");
		}

		this.categorieService.deleteCategorie(slug);
		return ResponseEntity.ok("Catégorie supprimée");
	}
}
