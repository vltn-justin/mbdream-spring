package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.InfoRequest;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.responses.InfoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for Info
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app"}) // Authorize angular
@RestController
@RequestMapping("/info")
public class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(final InfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * Method to add info to moto
     *
     * @param infoRequest Request with all data
     *
     * @return ResponseEntity<String>
     */
    @PostMapping("/add")
    public ResponseEntity<String> addInfo(@RequestBody final InfoRequest infoRequest) {
        if (this.infoService.addInfo(infoRequest).getIdInfo() != null) {
            return ResponseEntity.ok("Infos ajoutés");
        }
        return ResponseEntity.ok("Impossible d'ajouter les infos, essayez à nouveau");
    }

    /**
     * Method to update info
     *
     * @param infoRequest InfoRequest
     *
     * @return ResponseEntity<String>
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateInfo(@RequestBody final InfoRequest infoRequest) {
        if (this.infoService.update(infoRequest) != null) {
            return ResponseEntity.ok("Infos mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour les infos");
    }

    /**
     * Method to get info of moto
     *
     * @param slugMoto Slug of moto
     *
     * @return ResponseEntity Map<String, Object> or <String>
     */
    @GetMapping("/get/{slugMoto}")
    public ResponseEntity<?> getInfoMoto(@PathVariable final String slugMoto) {
        InfoModel infos = this.infoService.getInfoMoto(slugMoto);

        if (infos == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pas d'infos pour cette moto");
        }

        return ResponseEntity.ok(new InfoResponse().buildResponse(ResponseType.BASIC, infos));
    }
}
