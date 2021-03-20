package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.DTO.MediaRequest;
import com.chamalo.mbdream.models.ImageModel;
import com.chamalo.mbdream.models.VideoModel;
import com.chamalo.mbdream.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * Rest Controller for Medias (Image & Video)
 *
 * @author Chamalo
 */
@CrossOrigin(origins = {"http://localhost:4200",
        "https://motorbike-dream.web.app/"}) // Authorize angular
@RestController
@RequestMapping("/media")
public class MediaController {

    // TODO : Optimisation sur l'ajout d'un média, 1 seul méthode au lieu de 2

    private final MediaService mediaService;

    @Autowired
    public MediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * Method to add a media to Moto
     *
     * @param mediaRequest Request with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/add-media")
    public ResponseEntity<String> addMedia(@ModelAttribute final MediaRequest mediaRequest) throws IOException {
        if (mediaRequest.getIsVideo()) {
            if (this.mediaService.addVideo(mediaRequest).getIdVideo() != null) {
                return ResponseEntity.ok("Vidéo ajouter à la moto");
            }
            return ResponseEntity.ok("Impossible d'ajouter le media, essayez à nouveau");
        }

        if (this.mediaService.addImage(mediaRequest).getIdImage() != null) {
            return ResponseEntity.ok("Image ajouter à la moto");
        }

        return ResponseEntity.ok("Impossible d'ajouter le media, essayez à nouveau");
    }

    /**
     * Method to find all videos
     *
     * @return ResponseEntity Iterable of ImageModel
     */
    @GetMapping("/video")
    public ResponseEntity<Iterable<VideoModel>> findAllVideo() {
        return ResponseEntity.ok(this.mediaService.findAllVideo());
    }

    /**
     * Method to find a video with is Id
     *
     * @param id Id of video
     *
     * @return ResponseEntity with video or notfound
     */
    @GetMapping("/video/{id}")
    public ResponseEntity<VideoModel> findVideoById(@PathVariable final String id) {
        VideoModel video = this.mediaService.findVideoById(id);

        if (video != null) {
            return ResponseEntity.ok(video);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to update a video
     *
     * @param mediaRequest Request with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/video/update")
    public ResponseEntity<String> updateVideo(@RequestBody final MediaRequest mediaRequest) {
        if (this.mediaService.updateVideo(mediaRequest) != null) {
            return ResponseEntity.ok("Vidéo mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour la vidéo, essayez à nouveau");
    }

    /**
     * Method to delete a video with is id
     *
     * @param id Id of video to remove
     *
     * @return ResponseEntity
     */
    @GetMapping("/video/delete/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable final String id) {
        this.mediaService.deleteVideo(id);
        return ResponseEntity.ok("Video supprimée");
    }

    /**
     * Method to find all images
     *
     * @return ResponseEntity Iterable of ImageModel
     */
    @GetMapping("/img")
    public ResponseEntity<Iterable<ImageModel>> findAllImage() {
        return ResponseEntity.ok(this.mediaService.findAllImage());
    }

    /**
     * Method to find an image with is Id
     *
     * @param id Id of image
     *
     * @return ResponseEntity with image or notfound
     */
    @GetMapping("/img/{id}")
    public ResponseEntity<ImageModel> findImageById(@PathVariable final String id) {
        ImageModel image = this.mediaService.findImageById(id);

        if (image != null) {
            return ResponseEntity.ok(image);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Method to update an image
     *
     * @param mediaRequest Request with all data
     *
     * @return ResponseEntity
     */
    @PostMapping("/img/update")
    public ResponseEntity<String> updateImage(@RequestBody MediaRequest mediaRequest) {
        if (this.mediaService.updateImage(mediaRequest) != null) {
            return ResponseEntity.ok("Image mise à jour");
        }
        return ResponseEntity.ok("Impossible de mettre à jour l'image, essayez à nouveau");
    }

    /**
     * Method to delete an image with is id
     *
     * @param id Id of image to remove
     *
     * @return ResponseEntity
     */
    @GetMapping("/img/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable final String id) {
        this.mediaService.deleteImage(id);
        return ResponseEntity.ok("Image supprimée");
    }

    /**
     * Method to get all img for a moto
     *
     * @param idMoto ID of mato
     *
     * @return List<ImageModel>
     */
    @GetMapping("/img/get-all-moto/{idMoto}")
    public ResponseEntity<List<ImageModel>> getAllImgOfMoto(@PathVariable final Long idMoto) {
            List<ImageModel> imageModelList = this.mediaService.getAllImgOfMoto(idMoto);

            return ResponseEntity.ok(imageModelList);
    }

    /**
     * Method to get all video for a moto
     *
     * @param idMoto ID of mato
     *
     * @return List<VideoModel>
     */
    @GetMapping("/video/get-all-moto/{idMoto}")
    public ResponseEntity<List<VideoModel>> getAllVideoOfMoto(@PathVariable final Long idMoto) {
        List<VideoModel> videoModelList = this.mediaService.getAllVideoOfMoto(idMoto);

        return ResponseEntity.ok(videoModelList);
    }
}
