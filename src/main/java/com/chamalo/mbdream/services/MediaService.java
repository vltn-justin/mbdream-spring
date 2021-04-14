package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MediaRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MediaModel;
import com.chamalo.mbdream.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MediaService {
    private final MotoService motoService;

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(final MediaRepository mediaRepository, final MotoService motoService) {
        this.mediaRepository = mediaRepository;
        this.motoService = motoService;
    }

    public MediaModel addMedia(final MediaRequest mediaRequest) throws IOException {
        MediaModel mediaModel = new MediaModel();

        if (mediaRequest.getUrlMedia() != null && mediaRequest.getUrlMedia().length() > 1) {
            // Si on ajoute le media via lien
            mediaModel.setLienMedia(mediaRequest.getUrlMedia());
        } else {
            this.saveMediaIntoFolder(mediaRequest);

            mediaModel.setLienMedia("http://chamalo-web.ddns.net:16650/media/video/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename());
        }

        mediaModel.setDescriptionMedia(mediaRequest.getDescriptionMedia());
        mediaModel.setIsVideo(mediaRequest.getIsVideo());
        mediaModel.setMoto(motoService.findMotoBySlug(mediaRequest.getSlugMoto()));

        return this.mediaRepository.save(mediaModel);
    }

    public Iterable<MediaModel> findAllMedia(final String slugMoto, final Boolean isVideo) {
        return this.mediaRepository.findAllMediaOfMoto(slugMoto, isVideo).orElseThrow(
                () -> new MBDreamException("Impossible de trouver des médias pour le slug " + slugMoto)
        );
    }

    public void deleteMedia(final String idMedia) {
        this.mediaRepository.deleteById(Long.parseLong(idMedia));
    }

    /**
     * Method to save a media into folder
     *
     * @param mediaRequest MediaRequest
     *
     * @throws IOException File exception
     */
    private void saveMediaIntoFolder(final MediaRequest mediaRequest) throws IOException {
        // Changer le path pour le final
        String path = "/home/pi/mbdream-spring/resources/static/";

        path += (mediaRequest.getIsVideo() ? "videos/moto/" : "images/moto/");
        path += mediaRequest.getSlugMoto();

        final String pathFile = path + "/" + mediaRequest.getFileMedia().getOriginalFilename();

        final File folder = new File(path);

        // Create folder if doesn't exist
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new MBDreamException("Erreur à la création du dossier de sauvegarde");
            }
        }

        final File file = new File(pathFile);

        mediaRequest.getFileMedia().transferTo(file);
    }
}
