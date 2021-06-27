package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.MotoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.responses.MotoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Moto
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200", "https://motorbike-dream.web.app"})
@RestController
@RequestMapping("/motos")
public class MotoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MotoController.class);

	private final MotoService motoService;

	@Autowired
	public MotoController(final MotoService motoService) {
		this.motoService = motoService;
	}

	/**
	 * Method to add a Moto to database
	 *
	 * @param motoDTO MotoRequest with all data
	 * @return ResponseEntity
	 */
	@PostMapping
	public ResponseEntity<String> addMoto(@RequestBody final MotoDTO motoDTO) {
		final MotoModel moto = this.motoService.addMoto(motoDTO);
		if (moto.getIdMoto() != null) {
			return ResponseEntity.ok("Moto ajoutée - " + moto.getSlugMoto());
		}
		return ResponseEntity.status(500).body("Impossible d'ajouter la moto, essayez à nouveau");
	}

	/**
	 * Method to update data of a Moto
	 *
	 * @param slugMoto Slug of moto
	 * @param motoDTO MotoRequest with all data
	 * @return MBDreamException if not found, updatedmoto otherwise
	 */
	@PutMapping("/{slugMoto}")
	public ResponseEntity<String> updateMoto(@PathVariable final String slugMoto, @RequestBody final MotoDTO motoDTO) {
		try {
			motoDTO.setSlugMoto(slugMoto);
			this.motoService.updateMoto(motoDTO);
			return ResponseEntity.ok("Moto mise à jour");
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	/**
	 * Method to find a Moto with is slug
	 *
	 * @param slug Slug
	 * @return ResponseEntity of MotoModel
	 */
	@GetMapping(value = "/{slug}")
	public ResponseEntity<Object> findMotoBySlug(@PathVariable final String slug) {
		try {
			final var moto = this.motoService.findMotoBySlug(slug);
			return ResponseEntity.ok(new MotoResponse().buildResponse(ResponseType.BASIC, moto));
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	/**
	 * Method to get all moto, limited by 10 per page
	 *
	 * @return Response Entity
	 */
	@GetMapping
	public ResponseEntity<Object> findMotoByPage(@RequestParam(required = false, defaultValue = "0") final Integer page) {
		try {
			final Collection<MotoModel> allMoto = this.motoService.findMotoByPage(page);

			final Map<String, Object> mapResponse = new HashMap<>();
			final Long count = this.countAllMoto().getBody();
			mapResponse.put("count", count);

			if (count == null) {
				mapResponse.put("haveNext", false);
			} else {
				mapResponse.put("haveNext", count / 10 > page);
			}

			return listMotoToMap(allMoto, mapResponse);
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
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
	 * Method to delete a moto with is slug
	 *
	 * @param slug Slug of moto
	 * @return ResponseEntity
	 */
	@DeleteMapping("/{slug}")
	public ResponseEntity<String> deleteMoto(@PathVariable final String slug) {
		try {
			this.motoService.deleteMoto(slug);
			return ResponseEntity.ok("Moto supprimée");
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	/**
	 * Method to build response for each moto from a Collection
	 *
	 * @param motoModels  Collection of MotoModels
	 * @param mapResponse Map where to put response
	 * @return ResponseEntity
	 */
	private ResponseEntity<Object> listMotoToMap(final Collection<MotoModel> motoModels, final Map<String, Object> mapResponse) {
		final List<Map<String, Object>> mapList = new ArrayList<>();

		for (final MotoModel moto : motoModels) {
			mapList.add(new MotoResponse().buildResponse(ResponseType.LIGHT, moto));
		}

		mapResponse.put("results", mapList);

		return ResponseEntity.ok(mapResponse);
	}
}
