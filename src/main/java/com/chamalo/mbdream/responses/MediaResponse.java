package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MediaModel;

import java.util.Map;

public class MediaResponse extends RESPONSE<MediaModel> {
    @Override
    protected void basicResponse(final Map<String, Object> map, final MediaModel model) {
        map.put("idMedia", model.getIdMedia());
        map.put("lienMedia", model.getLienMedia());
        map.put("descriptionMedia", model.getDescriptionMedia());
    }

    @Override
    protected void infoResponse(final Map<String, Object> map, final MediaModel model) {
        // Pas d'info response
        this.basicResponse(map, model);
    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final MediaModel model) {
        // Pas de light response
        this.basicResponse(map, model);
    }
}
