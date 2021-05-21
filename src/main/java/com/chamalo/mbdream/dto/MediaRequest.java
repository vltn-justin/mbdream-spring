package com.chamalo.mbdream.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * MediaRequest Class, work for imageMoto & videoMoto
 *
 * @author Chamalo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaRequest {
    private Long idMedia;

    private String slugMoto;

    private MultipartFile fileMedia;

    private String urlMedia;

    private String descriptionMedia;

    private boolean isVideo;

    // Lombok ne genere pas bien les getter et setter pour le boolean

    /**
     * Getter for isVideo
     *
     * @return Boolean isVideo
     */
    public boolean getIsVideo () {
        return isVideo;
    }

    /**
     * Setter for isVideo
     *
     * @param isVideo Boolean isVideo
     */
    public void setIsVideo (final boolean isVideo) {
        this.isVideo = isVideo;
    }
}
