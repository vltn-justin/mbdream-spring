package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MotoModel;

import java.util.Map;

/**
 * Class to build a JSON response for Moto
 */
public class MotoResponse extends AbstractResponse<MotoModel> {

    @Override
    protected void basicResponse(final Map<String, Object> map, final MotoModel model) {
        this.lightResponse(map, model);

        this.infoResponse(map, model);

        map.put("descriptionMoto", model.getDescriptionMoto());
        map.put("nbMedia", model.getMedias().size());

        if (model.getInfos() != null) {
            map.put("idInfo", model.getInfos().getIdInfo());
        } else {
            map.put("idInfo", null);
        }
    }

    @Override
    protected void infoResponse(final Map<String, Object> map, final MotoModel model) {
        this.lightResponse(map, model);

        if (model.getCategorie() != null) {
            map.put("categorie", new CategorieResponse().buildResponse(ResponseType.LIGHT, model.getCategorie()));
        } else {
            map.put("categorie", null);
        }
        if (model.getMarque() != null) {
            map.put("marque", new MarqueResponse().buildResponse(ResponseType.LIGHT, model.getMarque()));
        } else {
            map.put("marque", null);
        }
    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final MotoModel model) {
        map.put("slugMoto", model.getSlugMoto());
        map.put("nomMoto", model.getNomMoto());
        map.put("backgroundImgMoto", model.getBackgroundImgMoto());
    }
}
