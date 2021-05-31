package com.chamalo.mbdream.responses;

import java.util.LinkedHashMap;
import java.util.Map;

public interface IResponse <M> {

	default Map<String, Object> buildResponse(final ResponseType type, final M model) {
		Map<String, Object> map = new LinkedHashMap<>();

		switch (type) {
			case INFO:
				infoResponse(map, model);
				break;
			case LIGHT:
				lightResponse(map, model);
				break;
			default:
				basicResponse(map, model);
				break;
		}

		return map;
	}

	void basicResponse(final Map<String, Object> map, final M model);

	void infoResponse(final Map<String, Object> map, final M model);

	void lightResponse(final Map<String, Object> map, final M model);
}
