package com.chamalo.mbdream.responses;

import com.chamalo.mbdream.models.MediaModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class MediaResponseTest {

	/**
	 * Test for {@link MediaResponse#basicResponse(Map, MediaModel)}
	 */
	@Test
	void testBasicResponse() {
		final MediaModel mediaModel = new MediaModel(1L, "lien", "description", false, null);

		final Map<String, Object> response = new MediaResponse().buildResponse(ResponseType.BASIC, mediaModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(mediaModel.getLienMedia(), response.get("lienMedia"));
		Assertions.assertEquals(mediaModel.getDescriptionMedia(), response.get("descriptionMedia"));
	}

	/**
	 * Test for {@link MediaResponse#infoResponse(Map, MediaModel)}
	 */
	@Test
	void testInfoResponse() {
		final MediaModel mediaModel = new MediaModel(1L, "lien", "description", false, null);

		final Map<String, Object> response = new MediaResponse().buildResponse(ResponseType.INFO, mediaModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(mediaModel.getLienMedia(), response.get("lienMedia"));
		Assertions.assertEquals(mediaModel.getDescriptionMedia(), response.get("descriptionMedia"));
	}

	/**
	 * Test for {@link MediaResponse#lightResponse(Map, MediaModel)}
	 */
	@Test
	void testLightResponse() {
		final MediaModel mediaModel = new MediaModel(1L, "lien", "description", false, null);

		final Map<String, Object> response = new MediaResponse().buildResponse(ResponseType.LIGHT, mediaModel);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(2, response.size());
		Assertions.assertEquals(mediaModel.getLienMedia(), response.get("lienMedia"));
		Assertions.assertEquals(mediaModel.getDescriptionMedia(), response.get("descriptionMedia"));
	}
}
