package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MediaRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MediaModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MediaRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
//            this.saveMediaIntoFolder(mediaRequest);
//
//            if (mediaRequest.getIsVideo()) {
//                mediaModel.setLienMedia("http://chamalo-web.ddns.net:16650/media/video/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename());
//            } else {
//                mediaModel.setLienMedia("http://chamalo-web.ddns.net:16650/media/images/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename());
//            }
            mediaModel.setLienMedia(this.uploadFile("image/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename(), mediaRequest.getFileMedia()));
        }

        mediaModel.setDescriptionMedia(mediaRequest.getDescriptionMedia());
        mediaModel.setIsVideo(mediaRequest.getIsVideo());
        mediaModel.setMoto(moto);

        mediaModel = this.mediaRepository.save(mediaModel);

        if (moto.getBackgroundImgMoto() == null && !mediaModel.getIsVideo()) {
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

    /**
     * Method to upload a file into firebase storage
     *
     * @param storageFilePath File path where the file will be saved
     * @param multipartFile   File to save
     *
     * @return URL of file saved
     *
     * @throws IOException Exception for FileInputStream
     */
    public String uploadFile(final String storageFilePath, final MultipartFile multipartFile) throws IOException {
        final FileInputStream serviceAccount = new FileInputStream(ResourceUtils.getFile("classpath:motorbike-dream-firebase-adminsdk-ddhec-044e9189f5.json"));

        final FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("motorbike-dream.appspot.com")
                .build();
        FirebaseApp.initializeApp(options);

        final Bucket bucket = StorageClient.getInstance().bucket();

        final InputStream tempFile = multipartFile.getInputStream();

        bucket.create(storageFilePath, tempFile, "media");
        // Make file readable public
        bucket.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        final String url = String.format("https://firebasestorage.googleapis.com/v0/b/motorbike-dream.appspot.com/o/%s?alt=media", URLEncoder.encode(storageFilePath, StandardCharsets.UTF_8));
        
        return url;
    }
}
