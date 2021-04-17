package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MotoModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to build a JSON response for Moto
 */
public class MotoResponse extends Response<MotoModel> {

    @Override
    protected void basicResponse(final Map<String, Object> map, final MotoModel model) {
        this.lightResponse(map, model);

        map.put("slugMoto", model.getSlugMoto());
        map.put("nomMoto", model.getNomMoto());
        map.put("descriptionMoto", model.getDescriptionMoto());

        if (model.getMarque() != null) {
            map.put("marque", new MarqueResponse().buildResponse(ResponseType.LIGHT, model.getMarque()));
        } else {
            map.put("marque", null);
        }

        if (model.getCategorie() != null) {
            map.put("categorie", new CategorieResponse().buildResponse(ResponseType.LIGHT, model.getCategorie()));
        } else {
            map.put("categorie", null);
        }

        map.put("nbMedia", model.getMedias().size());

        if (model.getInfos() != null) {
            map.put("idInfo", model.getInfos().getIdInfo());
        } else {
            map.put("idInfo", null);
        }
    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final MotoModel model) {
        map.put("slugMoto", model.getSlugMoto());
        map.put("nomMoto", model.getNomMoto());
        map.put("backgroundImgMoto", model.getBackgroundImgMoto());
    }
}
