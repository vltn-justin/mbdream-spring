package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MotoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class to build a JSON response for Categorie
 */
public class CategorieResponse implements IResponse<CategorieModel> {
	@Override
	public void basicResponse(final Map<String, Object> map, final CategorieModel categorie) {
		this.infoResponse(map, categorie);

		if (categorie.getMotos() != null) {
			map.put("motos", motoToMap(categorie.getMotos()));
		}

	}

	@Override
	public void infoResponse(final Map<String, Object> map, final CategorieModel categorie) {
		this.lightResponse(map, categorie);
	}

	@Override
	public void lightResponse(final Map<String, Object> map, final CategorieModel categorie) {
		map.put("slugCategorie", categorie.getSlugCategorie());
		map.put("nomCategorie", categorie.getNomCategorie());
	}

	private static List<Map<String, Object>> motoToMap(final Collection<MotoModel> motoModelCollection) {
		List<Map<String, Object>> listMoto = new ArrayList<>();

		for (MotoModel moto : motoModelCollection) {
			listMoto.add(new MotoResponse().buildResponse(ResponseType.INFO, moto));
		}

		return listMoto;
	}
}
