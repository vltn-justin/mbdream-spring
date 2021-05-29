package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

class MotoResponseTest {

	/**
	 * Test for {@link MotoResponse#lightResponse(Map, MotoModel)}
	 */
	@Test
	void testLightResponse() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null, null, null);

		final Map<String, Object> response = new MotoResponse().buildResponse(ResponseType.LIGHT, motoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(3, response.size());
		Assertions.assertEquals(motoModel.getSlugMoto(), response.get("slugMoto"));
		Assertions.assertEquals(motoModel.getNomMoto(), response.get("nomMoto"));
		Assertions.assertEquals(motoModel.getBackgroundImgMoto(), response.get("backgroundImgMoto"));
	}

	/**
	 * Test for {@link MotoResponse#infoResponse(Map, MotoModel)}
	 */
	@Test
	void testInfoResponse() {
		final CategorieModel categorieModel = new CategorieModel();

		final MarqueModel marqueModel = new MarqueModel();

		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", marqueModel,
				categorieModel, null, null);

		final Map<String, Object> response = new MotoResponse().buildResponse(ResponseType.INFO, motoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(5, response.size());
		Assertions.assertEquals(motoModel.getSlugMoto(), response.get("slugMoto"));
		Assertions.assertEquals(motoModel.getNomMoto(), response.get("nomMoto"));
		Assertions.assertEquals(motoModel.getBackgroundImgMoto(), response.get("backgroundImgMoto"));
		Assertions.assertNotNull(response.get("categorie"));
		Assertions.assertNotNull(response.get("marque"));
	}

	/**
	 * Test for {@link MotoResponse#infoResponse(Map, MotoModel)} with category & marque at null
	 */
	@Test
	void testInfoResponseNull() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null, null, null);

		final Map<String, Object> response = new MotoResponse().buildResponse(ResponseType.INFO, motoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(5, response.size());
		Assertions.assertEquals(motoModel.getSlugMoto(), response.get("slugMoto"));
		Assertions.assertEquals(motoModel.getNomMoto(), response.get("nomMoto"));
		Assertions.assertEquals(motoModel.getBackgroundImgMoto(), response.get("backgroundImgMoto"));
		Assertions.assertNull(response.get("categorie"));
		Assertions.assertNull(response.get("marque"));
	}

	/**
	 * Test for {@link MotoResponse#basicResponse(Map, MotoModel)}
	 */
	@Test
	void testBasicResponse() {
		final InfoModel infoModel = new InfoModel();
		infoModel.setIdInfo(1L);

		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null,
				Collections.emptyList(), infoModel);

		final Map<String, Object> response = new MotoResponse().buildResponse(ResponseType.BASIC, motoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(8, response.size());
		Assertions.assertEquals(motoModel.getSlugMoto(), response.get("slugMoto"));
		Assertions.assertEquals(motoModel.getNomMoto(), response.get("nomMoto"));
		Assertions.assertEquals(motoModel.getBackgroundImgMoto(), response.get("backgroundImgMoto"));
		Assertions.assertNull(response.get("categorie"));
		Assertions.assertNull(response.get("marque"));
		Assertions.assertEquals(motoModel.getDescriptionMoto(), response.get("descriptionMoto"));
		Assertions.assertEquals(motoModel.getMedias().size(), response.get("nbMedia"));
		Assertions.assertEquals(1L, response.get("idInfo"));
	}

	/**
	 * Test for {@link MotoResponse#basicResponse(Map, MotoModel)} with info null
	 */
	@Test
	void testBasicResponseNull() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null,
				Collections.emptyList(), null);

		final Map<String, Object> response = new MotoResponse().buildResponse(ResponseType.BASIC, motoModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(8, response.size());
		Assertions.assertEquals(motoModel.getSlugMoto(), response.get("slugMoto"));
		Assertions.assertEquals(motoModel.getNomMoto(), response.get("nomMoto"));
		Assertions.assertEquals(motoModel.getBackgroundImgMoto(), response.get("backgroundImgMoto"));
		Assertions.assertNull(response.get("categorie"));
		Assertions.assertNull(response.get("marque"));
		Assertions.assertEquals(motoModel.getDescriptionMoto(), response.get("descriptionMoto"));
		Assertions.assertEquals(motoModel.getMedias().size(), response.get("nbMedia"));
		Assertions.assertNull(response.get("idInfo"));
	}

}
