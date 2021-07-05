package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MotoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to build a JSON response for Moto
 */
public class MotoResponse implements IResponse<MotoModel> {

    public List<Map<String, Object>> listResponse(final Collection<MotoModel> models) {
        final var motos = new ArrayList<Map<String, Object>>();

        for (final var moto : models) {
            final var mapMoto = new LinkedHashMap<String, Object>();
            this.lightResponse(mapMoto, moto);
            motos.add(mapMoto);
        }

        return motos;
    }

    @Override
    public void basicResponse(final Map<String, Object> map, final MotoModel model) {
        this.lightResponse(map, model);

        this.infoResponse(map, model);

        map.put("descriptionMoto", model.getDescriptionMoto());

        if (model.getMedias() != null) {
            map.put("nbMedia", model.getMedias().size());
        } else {
            map.put("nbMedia", 0);
        }

        if (model.getInfos() != null) {
            map.put("idInfo", model.getInfos().getIdInfo());
        } else {
            map.put("idInfo", null);
        }
    }

    @Override
    public void infoResponse(final Map<String, Object> map, final MotoModel model) {
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
    public void lightResponse(final Map<String, Object> map, final MotoModel model) {
        map.put("slugMoto", model.getSlugMoto());
        map.put("nomMoto", model.getNomMoto());
        map.put("backgroundImgMoto", model.getBackgroundImgMoto());
    }
}
