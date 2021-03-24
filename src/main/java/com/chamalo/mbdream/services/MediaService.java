package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.MediaRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.ImageModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.models.VideoModel;
import com.chamalo.mbdream.repositories.ImageRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import com.chamalo.mbdream.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MediaService {

    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final MotoRepository motoRepository;

    @Autowired
    public MediaService(final ImageRepository imageRepository, final VideoRepository videoRepository, final MotoRepository motoRepository) {
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.motoRepository = motoRepository;
    }

    /**
     * Method to add a video to moto
     *
     * @param mediaRequest Request with all data
     *
     * @return VideoModel
     */
    public VideoModel addVideo(final MediaRequest mediaRequest) throws IOException {
        MotoModel moto = this.motoRepository.findMotoBySlug(mediaRequest.getSlugMoto()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la moto")
        );

        VideoModel video = new VideoModel();

        if (mediaRequest.getUrlMedia() != null && mediaRequest.getUrlMedia().length() > 1) {
            video.setLienVideo(mediaRequest.getUrlMedia());
        } else {
            this.saveMediaIntoFolder(mediaRequest);

            video.setLienVideo("http://chamalo-web.ddns.net:16650/media/video/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename());
        }

        video.setDescriptionVideo(mediaRequest.getDescriptionMedia());
        video.setMoto(moto);
        video = this.videoRepository.save(video);

        // Si l'id est null il y a un pb a l'enregistrement on retourne directement
        if (video.getIdVideo() == null) {
            return video;
        }

        moto.addVideo(video);
        this.motoRepository.save(moto);

        return video;
    }

    /**
     * Method to add an image to moto
     *
     * @param mediaRequest Request with all data
     *
     * @return ImageModel
     */
    public ImageModel addImage(final MediaRequest mediaRequest) throws IOException {
        MotoModel moto = this.motoRepository.findMotoBySlug(mediaRequest.getSlugMoto()).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la moto")
        );

        ImageModel image = new ImageModel();

        if (mediaRequest.getUrlMedia() != null && mediaRequest.getUrlMedia().length() > 1) {
            image.setLienImage(mediaRequest.getUrlMedia());
        } else {
            this.saveMediaIntoFolder(mediaRequest);

            image.setLienImage("http://chamalo-web.ddns.net:16650/media/img/moto/" + mediaRequest.getSlugMoto() + "/" + mediaRequest.getFileMedia().getOriginalFilename());
        }

        image.setDescriptionImage(mediaRequest.getDescriptionMedia());
        image.setMoto(moto);
        image = this.imageRepository.save(image);

        // Si l'id est null il y a un pb a l'enregistrement on retourne directement
        if (image.getIdImage() == null) {
            return image;
        }

        if(moto.getImages().size() == 0) {
            moto.setBackgroundImgMoto(image.getLienImage());
        }
        moto.addImage(image);

        this.motoRepository.save(moto);

        return image;
    }

    /**
     * Method to find all videos
     *
     * @return Iterable of ImageModel
     */
    public Iterable<VideoModel> findAllVideo() {
        return this.videoRepository.findAll();
    }

    /**
     * Method to find a video with is Id
     *
     * @param id Id of video
     *
     * @return VideoModel or MBDreamException
     */
    public VideoModel findVideoById(final String id) {
        return this.videoRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new MBDreamException("Impossible de trouver la vidéo avec l'id " + id)
        );
    }

    /**
     * Method to update a video
     *
     * @param mediaRequest Request with all data
     *
     * @return Updated VideoModel or null
     */
    public VideoModel updateVideo(final MediaRequest mediaRequest) {
        Optional<VideoModel> video = this.videoRepository.findById(mediaRequest.getIdMedia());

        if (video.isPresent()) {
            VideoModel videoUpdated = video.get();

            videoUpdated.setDescriptionVideo(mediaRequest.getDescriptionMedia());

            return this.videoRepository.save(videoUpdated);
        }

        return null;
    }

    /**
     * Method to delete a video with is id
     *
     * @param id Id of video to remove
     */
    public void deleteVideo(final String id) {
        this.videoRepository.deleteById(Long.parseLong(id));
    }

    /**
     * Method to find all images
     *
     * @return Iterable of ImageModel
     */
    public Iterable<ImageModel> findAllImage() {
        return this.imageRepository.findAll();
    }

    /**
     * Method to find an image with is Id
     *
     * @param id Id of image
     *
     * @return ImageModel or MBDreamException
     */
    public ImageModel findImageById(final String id) {
        return this.imageRepository.findById(Long.parseLong(id)).orElseThrow(
                () -> new MBDreamException("Image introuvable avec l'id " + id)
        );
    }

    /**
     * Method to update an image
     *
     * @param mediaRequest Request with all data
     *
     * @return Updated imagemodel or null
     */
    public ImageModel updateImage(final MediaRequest mediaRequest) {
        Optional<ImageModel> image = this.imageRepository.findById(mediaRequest.getIdMedia());

        if (image.isPresent()) {
            ImageModel imageUpdated = image.get();

            imageUpdated.setDescriptionImage(mediaRequest.getDescriptionMedia());

            return this.imageRepository.save(imageUpdated);
        }

        return null;
    }

    /**
     * Method to delete an image with is id
     *
     * @param id Id of image to remove
     */
    public void deleteImage(final String id) {
        this.imageRepository.deleteById(Long.parseLong(id));
    }

    public List<ImageModel> getAllImgOfMoto(final Long idMoto) {
        Optional<List<ImageModel>> imageModelList = this.imageRepository.findAllImgOfMoto(idMoto);

        return imageModelList.orElse(Collections.emptyList());
    }

    public List<VideoModel> getAllVideoOfMoto(final Long idMoto) {
        Optional<List<VideoModel>> videoModelList = this.videoRepository.findAllVideoOfMoto(idMoto);

        return videoModelList.orElse(Collections.emptyList());
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
            folder.mkdir();
        }

        final File file = new File(pathFile);

        mediaRequest.getFileMedia().transferTo(file);
    }
}
