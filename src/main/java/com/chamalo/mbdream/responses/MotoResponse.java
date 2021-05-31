package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MotoModel;

import java.util.Map;

/**
 * Class to build a JSON response for Moto
 */
public class MotoResponse implements IResponse<MotoModel> {

	@Override
	public void basicResponse(final Map<String, Object> map, final MotoModel model) {
		this.lightResponse(map, model);

		this.infoResponse(map, model);

		map.put("descriptionMoto", model.getDescriptionMoto());

		if(model.getMedias() != null) {
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
