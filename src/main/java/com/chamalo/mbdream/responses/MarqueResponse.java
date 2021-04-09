package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;

import java.util.*;

/**
 * Class to build a JSON response for Marque
 */
public class MarqueResponse {

    public static Map<String, Object> buildResponse(final ResponseType type, final MarqueModel marque) {
        Map<String, Object> map = new LinkedHashMap<>();

        switch (type) {
            case BASIC:
                basicResponse(map, marque);
                break;
            case LIGHT:
                lightResponse(map, marque);
                break;
        }

        return map;
    }

    private static void basicResponse(final Map<String, Object> map, final MarqueModel marque) {
        lightResponse(map, marque);

        map.put("dateCreation", marque.getDateCreation());
        map.put("descriptionMarque", marque.getDescriptionMarque());
        map.put("logoMarque", marque.getLogoMarque());

        if (marque.getMotos() != null) {
            map.put("motos", motoToMap(marque.getMotos()));
        }

    }

    private static void lightResponse(final Map<String, Object> map, final MarqueModel marque) {
        map.put("nomMarque", marque.getNomMarque());
        map.put("slugMarque", marque.getSlugMarque());
    }

    private static List<Map<String, Object>> motoToMap(final Collection<MotoModel> motoModelCollection) {
        List<Map<String, Object>> listMoto = new ArrayList<>();

        for(MotoModel moto : motoModelCollection) {
            listMoto.add(MotoResponse.buildResponse(ResponseType.LIGHT, moto));
        }

        return listMoto;
    }
}
