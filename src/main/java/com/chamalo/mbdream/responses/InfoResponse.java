package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.InfoModel;

import java.util.Map;

public class InfoResponse extends RESPONSE<InfoModel> {

    @Override
    protected void basicResponse(final Map<String, Object> map, final InfoModel model) {
        map.put("prix", model.getPrix());
        map.put("architectureMoteur", model.getArchitectureMoteur());
        map.put("cylindre", model.getCylindre());
        map.put("puissance", model.getPuissance());
        map.put("couple", model.getCouple());
        map.put("poid", model.getPoid());
        map.put("capaciteReservoir", model.getCapaciteReservoir());
        map.put("consommation", model.getConsommation());
    }

    @Override
    protected void infoResponse(final Map<String, Object> map, final InfoModel model) {
        // Pas d'info response
        this.basicResponse(map, model);
    }

    @Override
    protected void lightResponse(final Map<String, Object> map, final InfoModel model) {
        // Pas de light response pour les infos
        basicResponse(map, model);
    }
}
