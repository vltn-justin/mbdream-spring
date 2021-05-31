package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.services.MotoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class MotoControllerTest {

	@MockBean private MotoService service;

	@Autowired private MotoController controller;

	/**
	 * Test OK for {@link MotoController#findMotoBySlug(String)}
	 */
	@Test
	void testFindBySlugOK() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null, null, null);

		Mockito.when(this.service.findMotoBySlug("test")).thenReturn(motoModel);

		final ResponseEntity<Object> response = this.controller.findMotoBySlug("test");

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(
				"{slugMoto=slug-moto, nomMoto=Moto, backgroundImgMoto=bgc.png, categorie=null, marque=null, descriptionMoto=description, nbMedia=0, idInfo=null}",
				response.getBody().toString());
	}

	/**
	 * Test KO for {@link MotoController#findMotoBySlug(String)}
	 */
	@Test
	void testFindBySlugKO() {
		Mockito.when(this.service.findMotoBySlug("test")).thenThrow(new MBDreamException("Moto introuvable"));

		final ResponseEntity<Object> response = this.controller.findMotoBySlug("test");

		Assertions.assertEquals(404, response.getStatusCode().value());
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals("Moto introuvable", response.getBody().toString());
	}

	/**
	 * Test for {@link MotoController#countAllMoto()}
	 */
	@Test
	void testCount() {
		Mockito.when(this.service.countAllMoto()).thenReturn(5L);

		final ResponseEntity<Long> count = this.controller.countAllMoto();

		Assertions.assertNotNull(count);
		Assertions.assertEquals(5L, count.getBody());
	}

	/**
	 * Test OK for {@link MotoController#deleteMoto(String)}
	 */
	@Test
	void testDeleteOK() {
		Mockito.doNothing().when(this.service).deleteMoto("test");

		final ResponseEntity<String> response = this.controller.deleteMoto("test");

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertEquals("Moto supprimée", response.getBody());
	}

	/**
	 * Test KO for {@link MotoController#deleteMoto(String)}
	 */
	@Test
	void testDeleteKO() {
		Mockito.doThrow(MBDreamException.class).when(this.service).deleteMoto("test");

		final ResponseEntity<String> response = this.controller.deleteMoto("test");

		Assertions.assertEquals(500, response.getStatusCode().value());
		Assertions.assertNull(response.getBody());
	}

}
