package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.DTO.InfoRequest;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.responses.InfoResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("/add-info")
    public ResponseEntity<String> addInfo(@RequestBody final InfoRequest infoRequest) {
        if (this.infoService.addInfo(infoRequest).getIdInfo() != null) {
            return ResponseEntity.ok("Infos ajoutés");
        }
        return ResponseEntity.ok("Impossible d'ajouter les infos, essayez à nouveau");
    }

    /**
     * Method to get info of moto
     *
     * @param slugMoto Slug of moto
     *
     * @return ResponseEntity<InfoModel>
     */
    @GetMapping("/get/moto/{slugMoto}")
    public ResponseEntity<Map<String, Object>> getInfoMoto(@PathVariable final String slugMoto) {
        InfoModel infos = this.infoService.getInfoMoto(slugMoto);

        if (infos == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new InfoResponse().buildResponse(ResponseType.BASIC, infos));
    }

    /**
     * Method to get info of moto
     *
     * @param idInfo id of info to get
     *
     * @return ResponseEntity<InfoModel>
     */
    @GetMapping("/get/{idInfo}")
    public ResponseEntity<InfoModel> getInfo(@PathVariable final String idInfo) {
        InfoModel infos = this.infoService.getInfo(idInfo);

        if (infos == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(infos);
    }


}
