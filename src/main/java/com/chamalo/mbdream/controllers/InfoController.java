package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.InfoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.responses.InfoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for Info
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200", "https://motorbike-dream.web.app"})
@RestController
@RequestMapping("/infos")
public class InfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(InfoController.class);

	private final InfoService infoService;

	@Autowired
	public InfoController(final InfoService infoService) {
		this.infoService = infoService;
	}

	/**
	 * Method to add info to moto
	 *
	 * @param infoDTO  Request with all data
	 * @return ResponseEntity<String>
	 */
	@PostMapping("")
	public ResponseEntity<String> addInfo(@RequestBody final InfoDTO infoDTO) {
		try {
			this.infoService.addInfo(infoDTO);
			return ResponseEntity.ok("Infos ajoutés");
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
			return ResponseEntity.status(500).body("Impossible d'ajouter les infos, essayez à nouveau");
		}
	}

	/**
	 * Method to update info
	 *
	 * @param slugMoto Slug of moto
	 * @param infoDTO  InfoRequest
	 * @return ResponseEntity<String>
	 */
	@PutMapping("/{slugMoto}")
	public ResponseEntity<String> updateInfo(@PathVariable final String slugMoto, @RequestBody final InfoDTO infoDTO) {
		try {
			infoDTO.setSlugMoto(slugMoto);
			this.infoService.update(infoDTO);
			return ResponseEntity.ok("Infos mise à jour");
		} catch (final MBDreamException e) {
			LOGGER.warn(e.getMessage(), e);
			return ResponseEntity.status(500).body("Impossible de mettre à jour les infos");
		}
	}

	/**
	 * Method to get info of moto
	 *
	 * @param slugMoto Slug of moto
	 * @return ResponseEntity Map<String, Object> or <String>
	 */
	@GetMapping("/{slugMoto}")
	public ResponseEntity<Object> getInfoMoto(@PathVariable final String slugMoto) {
		InfoModel infos = this.infoService.findInfoMoto(slugMoto);

		if (infos == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pas d'infos pour cette moto");
		}

		return ResponseEntity.ok(new InfoResponse().buildResponse(ResponseType.BASIC, infos));
	}
}
