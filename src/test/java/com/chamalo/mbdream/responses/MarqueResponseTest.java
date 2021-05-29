package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

class MarqueResponseTest {

	/**
	 * Test for {@link MarqueResponse#basicResponse(Map, MarqueModel)} with moto null
	 */
	@Test
	void testBasicResponseNull() {
		final MarqueModel marqueModel = new MarqueModel(1L, "slug", "nom", null, "description", "logo", null);

		final Map<String, Object> response = new MarqueResponse().buildResponse(ResponseType.BASIC, marqueModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(6, response.size());
		Assertions.assertEquals(marqueModel.getSlugMarque(), response.get("slugMarque"));
		Assertions.assertEquals(marqueModel.getNomMarque(), response.get("nomMarque"));
		Assertions.assertEquals(marqueModel.getLogoMarque(), response.get("logoMarque"));
		Assertions.assertNull(response.get("dateCreation"));
		Assertions.assertEquals(marqueModel.getDescriptionMarque(), response.get("descriptionMarque"));
		Assertions.assertNull(response.get("motos"));
	}

	/**
	 * Test for {@link MarqueResponse#basicResponse(Map, MarqueModel)} with moto not null
	 */
	@Test
	void testBasicResponse() {
		final MotoModel motoModel = new MotoModel();

		final MarqueModel marqueModel = new MarqueModel(1L, "slug", "nom", null, "description", "logo", Collections.singleton(motoModel));

		final Map<String, Object> response = new MarqueResponse().buildResponse(ResponseType.BASIC, marqueModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(6, response.size());
		Assertions.assertEquals(marqueModel.getSlugMarque(), response.get("slugMarque"));
		Assertions.assertEquals(marqueModel.getNomMarque(), response.get("nomMarque"));
		Assertions.assertEquals(marqueModel.getLogoMarque(), response.get("logoMarque"));
		Assertions.assertNull(response.get("dateCreation"));
		Assertions.assertEquals(marqueModel.getDescriptionMarque(), response.get("descriptionMarque"));
		Assertions.assertNotNull(response.get("motos"));
	}

	/**
	 * Test for {@link MarqueResponse#infoResponse(Map, MarqueModel)}
	 */
	@Test
	void testInfoResponse() {
		final MarqueModel marqueModel = new MarqueModel(1L, "slug", "nom", null, "description", "logo", Collections.emptyList());

		final Map<String, Object> response = new MarqueResponse().buildResponse(ResponseType.INFO, marqueModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(3, response.size());
		Assertions.assertEquals(marqueModel.getSlugMarque(), response.get("slugMarque"));
		Assertions.assertEquals(marqueModel.getNomMarque(), response.get("nomMarque"));
		Assertions.assertEquals(marqueModel.getLogoMarque(), response.get("logoMarque"));
	}

	/**
	 * Test for {@link MarqueResponse#lightResponse(Map, MarqueModel)}
	 */
	@Test
	void testLightResponse() {
		final MarqueModel marqueModel = new MarqueModel(1L, "slug", "nom", null, "description", "logo", Collections.emptyList());

		final Map<String, Object> response = new MarqueResponse().buildResponse(ResponseType.LIGHT, marqueModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(marqueModel.getSlugMarque(), response.get("slugMarque"));
		Assertions.assertEquals(marqueModel.getNomMarque(), response.get("nomMarque"));
	}
}
