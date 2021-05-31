package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MotoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

class CategorieResponseTest {

	/**
	 * Test for {@link CategorieResponse#basicResponse(Map, CategorieModel)} with moto null
	 */
	@Test
	void testBasicResponseNull() {
		final CategorieModel categorieModel = new CategorieModel(1L, "slug", "nom", null);

		final Map<String, Object> response = new CategorieResponse().buildResponse(ResponseType.BASIC, categorieModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(3, response.size());
		Assertions.assertEquals(categorieModel.getSlugCategorie(), response.get("slugCategorie"));
		Assertions.assertEquals(categorieModel.getNomCategorie(), response.get("nomCategorie"));
		Assertions.assertNull(response.get("motos"));
	}

	/**
	 * Test for {@link CategorieResponse#basicResponse(Map, CategorieModel)}
	 */
	@Test
	void testBasicResponse() {
		final MotoModel motoModel = new MotoModel();

		final CategorieModel categorieModel = new CategorieModel(1L, "slug", "nom", Collections.singleton(motoModel));

		final Map<String, Object> response = new CategorieResponse().buildResponse(ResponseType.BASIC, categorieModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(3, response.size());
		Assertions.assertEquals(categorieModel.getSlugCategorie(), response.get("slugCategorie"));
		Assertions.assertEquals(categorieModel.getNomCategorie(), response.get("nomCategorie"));
		Assertions.assertNotNull(response.get("motos"));
	}

	@Test
	void testInfoResponse() {
		final CategorieModel categorieModel = new CategorieModel(1L, "slug", "nom", null);

		final Map<String, Object> response = new CategorieResponse().buildResponse(ResponseType.INFO, categorieModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(categorieModel.getSlugCategorie(), response.get("slugCategorie"));
		Assertions.assertEquals(categorieModel.getNomCategorie(), response.get("nomCategorie"));
	}

	@Test
	void testLightResponse() {
		final CategorieModel categorieModel = new CategorieModel(1L, "slug", "nom", null);

		final Map<String, Object> response = new CategorieResponse().buildResponse(ResponseType.INFO, categorieModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(categorieModel.getSlugCategorie(), response.get("slugCategorie"));
		Assertions.assertEquals(categorieModel.getNomCategorie(), response.get("nomCategorie"));
	}
}
