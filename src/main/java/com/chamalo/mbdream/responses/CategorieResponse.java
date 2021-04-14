package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MotoModel;

import java.util.*;

/**
 * Class to build a JSON response for Categorie
 */
public class CategorieResponse extends Response<CategorieModel>{
    @Override
    protected void basicResponse(final Map<String, Object> map, final CategorieModel categorie) {
        lightResponse(map, categorie);

        if (categorie.getMotos() != null) {
            map.put("motos", motoToMap(categorie.getMotos()));
        }

    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final CategorieModel categorie) {
        map.put("nomCategorie", categorie.getNomCategorie());
        map.put("slugCategorie", categorie.getSlugCategorie());
    }

    private static List<Map<String, Object>> motoToMap(final Collection<MotoModel> motoModelCollection) {
        List<Map<String, Object>> listMoto = new ArrayList<>();

        for (MotoModel moto : motoModelCollection) {
            listMoto.add(new MotoResponse().buildResponse(ResponseType.LIGHT, moto));
        }

        return listMoto;
    }
}
