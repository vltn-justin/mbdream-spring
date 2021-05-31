package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.InfoModel;

import java.util.Map;

public class InfoResponse implements IResponse<InfoModel> {

	@Override
	public void basicResponse(final Map<String, Object> map, final InfoModel model) {
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
	public void infoResponse(final Map<String, Object> map, final InfoModel model) {
		// Pas d'info response
		this.basicResponse(map, model);
	}

	@Override
	public void lightResponse(final Map<String, Object> map, final InfoModel model) {
		// Pas de light response pour les infos
		this.basicResponse(map, model);
	}
}
