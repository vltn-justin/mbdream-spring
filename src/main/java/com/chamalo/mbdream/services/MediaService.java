package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MediaRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MediaModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MediaRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final MotoRepository motoRepository;

    @Autowired
    public MediaService(final MediaRepository mediaRepository, final MotoRepository motoRepository) {
        this.mediaRepository = mediaRepository;
        this.motoRepository = motoRepository;
    }

    public MediaModel addMedia(final MediaRequest mediaRequest) throws IOException {
        final MotoModel moto = motoRepository.findMotoBySlug(mediaRequest.getSlugMoto()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la moto avec le slug " + mediaRequest.getSlugMoto())
        );

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
        mediaModel.setMoto(moto);

        mediaModel = this.mediaRepository.save(mediaModel);

        if (moto.getBackgroundImgMoto().length() == 0 && !mediaModel.getIsVideo()) {
            moto.setBackgroundImgMoto(mediaModel.getLienMedia());
            motoRepository.save(moto);
        }

        return mediaModel;
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
