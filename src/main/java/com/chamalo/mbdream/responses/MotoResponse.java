package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MotoModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to build a JSON response for Moto
 */
public class MotoResponse {

    public static Map<String, Object> buildResponse(final ResponseType type, final MotoModel moto) {
        Map<String, Object> map = new LinkedHashMap<>();

        switch (type) {
            case BASIC:
                basicResponse(map, moto);
                break;
            case LIGHT:
                lightResponse(map, moto);
                break;
        }

        return map;
    }

    private static void basicResponse(final Map<String, Object> map, final MotoModel moto) {
        map.put("idMoto", moto.getIdMoto());
        map.put("slugMoto", moto.getSlugMoto());
        map.put("nomMoto", moto.getNomMoto());
        map.put("prixMoto", moto.getPrixMoto());
        map.put("descriptionMoto", moto.getDescriptionMoto());
        map.put("backgroundImgMoto", moto.getBackgroundImgMoto());

        if (moto.getMarque() != null) {
            map.put("marque", MarqueResponse.buildResponse(ResponseType.LIGHT, moto.getMarque()));
        } else {
            map.put("marque", null);
        }

        if (moto.getCategorie() != null) {
            map.put("categorie", CategorieResponse.buildResponse(ResponseType.LIGHT, moto.getCategorie()));
        } else {
            map.put("categorie", null);
        }

        map.put("nbImages", moto.getImages().size());
        map.put("nbVideos", moto.getVideos().size());

        if (moto.getInfos() != null) {
            map.put("idInfo", moto.getInfos().getIdInfo());
        } else {
            map.put("idInfo", null);
        }
    }

    private static void lightResponse(final Map<String, Object> map, final MotoModel moto) {
        map.put("slugMoto", moto.getSlugMoto());
        map.put("nomMoto", moto.getNomMoto());
    }
}
