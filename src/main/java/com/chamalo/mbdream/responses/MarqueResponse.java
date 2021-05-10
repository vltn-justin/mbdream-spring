package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;

import java.util.*;

/**
 * Class to build a JSON response for Marque
 */
public class MarqueResponse extends Response<MarqueModel>{

    @Override
    protected void basicResponse(final Map<String, Object> map, final MarqueModel marque) {
        this.infoResponse(map, marque);

        map.put("dateCreation", marque.getDateCreation());
        map.put("descriptionMarque", marque.getDescriptionMarque());

        if (marque.getMotos() != null) {
            map.put("motos", motoToMap(marque.getMotos(), ResponseType.INFO));
        }

    }

    @Override
    protected void infoResponse(final Map<String, Object> map, final MarqueModel marque) {
        this.lightResponse(map, marque);
        map.put("logoMarque", marque.getLogoMarque());
    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final MarqueModel marque) {
        map.put("slugMarque", marque.getSlugMarque());
        map.put("nomMarque", marque.getNomMarque());
    }

    private static List<Map<String, Object>> motoToMap(final Collection<MotoModel> motoModelCollection, final ResponseType type) {
        List<Map<String, Object>> listMoto = new ArrayList<>();

        for(MotoModel moto : motoModelCollection) {
            listMoto.add(new MotoResponse().buildResponse(type, moto));
        }

        return listMoto;
    }
}
