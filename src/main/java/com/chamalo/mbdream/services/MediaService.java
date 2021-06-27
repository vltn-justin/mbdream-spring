package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.MediaDTO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;
    private final MotoRepository motoRepository;

    @Value("classpath:motorbike-dream-firebase-adminsdk-ddhec-d3f4212187.json")
    Resource firebaseCredentials;

    @Autowired
    public MediaService(final MediaRepository mediaRepository, final MotoRepository motoRepository) {
        this.mediaRepository = mediaRepository;
        this.motoRepository = motoRepository;
    }

    public MediaModel addMedia(final MediaDTO mediaDTO) throws IOException {
        final MotoModel moto = motoRepository.findMotoBySlug(mediaDTO.getSlugMoto()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la moto avec le slug " + mediaDTO.getSlugMoto())
        );

        MediaModel mediaModel = new MediaModel();

        if (mediaDTO.getUrlMedia() != null && mediaDTO.getUrlMedia().length() > 1) {
            // Si on ajoute le media via lien
            mediaModel.setLienMedia(mediaDTO.getUrlMedia());
        } else {
            mediaModel.setLienMedia(this.uploadFile(
                    "image/moto/" + mediaDTO.getSlugMoto() + "/" + mediaDTO.getFileMedia().getOriginalFilename(),
                    mediaDTO.getFileMedia()));
        }

        mediaModel.setDescriptionMedia(mediaDTO.getDescriptionMedia());
        mediaModel.setIsVideo(mediaDTO.getIsVideo());
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
        final InputStream serviceAccount = firebaseCredentials.getInputStream();

        final FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("motorbike-dream.appspot.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options, "mbdream_bucket");
        }

        final Bucket bucket = StorageClient.getInstance(FirebaseApp.getInstance("mbdream_bucket")).bucket();

        final InputStream tempFile = multipartFile.getInputStream();

        bucket.create(storageFilePath, tempFile, "media");
        // Make file readable public
        bucket.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return String.format("https://firebasestorage.googleapis.com/v0/b/motorbike-dream.appspot.com/o/%s?alt=media",
                URLEncoder.encode(storageFilePath, StandardCharsets.UTF_8));
    }
}
