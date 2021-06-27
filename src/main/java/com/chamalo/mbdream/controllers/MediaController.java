package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.dto.MediaDTO;
import com.chamalo.mbdream.models.MediaModel;
import com.chamalo.mbdream.responses.MediaResponse;
import com.chamalo.mbdream.responses.ResponseType;
import com.chamalo.mbdream.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Rest Controller for Medias
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200", "https://motorbike-dream.web.app"})
@RestController
@RequestMapping("/medias")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Method to add a Media
     *
     * @param mediaDTO Request with all data
     *
     * @return Response Entity
     *
     * @throws IOException Throw with file when you want to save media
     */
    @PostMapping("")
    public ResponseEntity<String> addMedia(@ModelAttribute final MediaDTO mediaDTO) throws IOException {
        if (this.mediaService.addMedia(mediaDTO).getIdMedia() != null) {
            return ResponseEntity.ok("Media ajouter à la moto");
        }

        return ResponseEntity.ok("Impossible d'ajouter le media, essayez à nouveau");
    }

    /**
     * Method to get all media from moto
     *
     * @param slugMoto Slug of moto
     *
     * @return ResponseEntity
     */
    @GetMapping("/{slugMoto}")
    public ResponseEntity<Object> getAllMedia(@PathVariable final String slugMoto,
                                              @RequestParam(required = false, defaultValue = "false") final Boolean isVideo) {
        Iterable<MediaModel> allMedia = this.mediaService.findAllMedia(slugMoto, isVideo);

        List<Map<String, Object>> mapList = new ArrayList<>();

        for (MediaModel media : allMedia) {
            mapList.add(new MediaResponse().buildResponse(ResponseType.BASIC, media));
        }

        return ResponseEntity.ok(mapList);
    }

    /**
     * Method to delete a media
     *
     * @param idMedia ID of media to delete
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/{idMedia}")
    public ResponseEntity<String> deleteMedia(@PathVariable final String idMedia) {
        this.mediaService.deleteMedia(idMedia);
        return ResponseEntity.ok("Media supprimer");
    }
}
