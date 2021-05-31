package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.models.MotoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class InfoResponseTest {

	/**
	 * Test for {@link InfoResponse#basicResponse(Map, InfoModel)}
	 */
	@Test
	void testBasicResponse() {
		final InfoModel infoModel = new InfoModel(1L, 5555, "architecture", 650, 155, 10, 180, 14D, 5.5D, new MotoModel());

		final Map<String, Object> response = new InfoResponse().buildResponse(ResponseType.BASIC, infoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(8, response.size());
		Assertions.assertEquals(infoModel.getPrix(), response.get("prix"));
		Assertions.assertEquals(infoModel.getArchitectureMoteur(), response.get("architectureMoteur"));
		Assertions.assertEquals(infoModel.getCylindre(), response.get("cylindre"));
		Assertions.assertEquals(infoModel.getPuissance(), response.get("puissance"));
		Assertions.assertEquals(infoModel.getCouple(), response.get("couple"));
		Assertions.assertEquals(infoModel.getPoid(), response.get("poid"));
		Assertions.assertEquals(infoModel.getCapaciteReservoir(), response.get("capaciteReservoir"));
		Assertions.assertEquals(infoModel.getConsommation(), response.get("consommation"));
	}

	/**
	 * Test for {@link InfoResponse#infoResponse(Map, InfoModel)}
	 */
	@Test
	void testInfoResponse() {
		final InfoModel infoModel = new InfoModel(1L, 5555, "architecture", 650, 155, 10, 180, 14D, 5.5D, new MotoModel());

		final Map<String, Object> response = new InfoResponse().buildResponse(ResponseType.INFO, infoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(8, response.size());
		Assertions.assertEquals(infoModel.getPrix(), response.get("prix"));
		Assertions.assertEquals(infoModel.getArchitectureMoteur(), response.get("architectureMoteur"));
		Assertions.assertEquals(infoModel.getCylindre(), response.get("cylindre"));
		Assertions.assertEquals(infoModel.getPuissance(), response.get("puissance"));
		Assertions.assertEquals(infoModel.getCouple(), response.get("couple"));
		Assertions.assertEquals(infoModel.getPoid(), response.get("poid"));
		Assertions.assertEquals(infoModel.getCapaciteReservoir(), response.get("capaciteReservoir"));
		Assertions.assertEquals(infoModel.getConsommation(), response.get("consommation"));
	}

	/**
	 * Test for {@link InfoResponse#lightResponse(Map, InfoModel)}
	 */
	@Test
	void testLightResponse() {
		final InfoModel infoModel = new InfoModel(1L, 5555, "architecture", 650, 155, 10, 180, 14D, 5.5D, new MotoModel());

		final Map<String, Object> response = new InfoResponse().buildResponse(ResponseType.LIGHT, infoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(8, response.size());
		Assertions.assertEquals(infoModel.getPrix(), response.get("prix"));
		Assertions.assertEquals(infoModel.getArchitectureMoteur(), response.get("architectureMoteur"));
		Assertions.assertEquals(infoModel.getCylindre(), response.get("cylindre"));
		Assertions.assertEquals(infoModel.getPuissance(), response.get("puissance"));
		Assertions.assertEquals(infoModel.getCouple(), response.get("couple"));
		Assertions.assertEquals(infoModel.getPoid(), response.get("poid"));
		Assertions.assertEquals(infoModel.getCapaciteReservoir(), response.get("capaciteReservoir"));
		Assertions.assertEquals(infoModel.getConsommation(), response.get("consommation"));
	}
}
