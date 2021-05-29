package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MediaModel;

import java.util.Map;

public class MediaResponse implements IResponse<MediaModel> {
	@Override
	public void basicResponse(final Map<String, Object> map, final MediaModel model) {
		map.put("idMedia", model.getIdMedia());
		map.put("lienMedia", model.getLienMedia());
		map.put("descriptionMedia", model.getDescriptionMedia());
	}

	@Override
	public void infoResponse(final Map<String, Object> map, final MediaModel model) {
		// Pas d'info response
		this.basicResponse(map, model);
	}

	@Override
	public void lightResponse(final Map<String, Object> map, final MediaModel model) {
		// Pas de light response
		this.basicResponse(map, model);
	}
}
