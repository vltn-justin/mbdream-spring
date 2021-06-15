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

import java.util.Collections;

@SpringBootTest
class MotoControllerTest {

	// TODO add, update

	@MockBean private MotoService service;

	@Autowired private MotoController controller;

	/**
	 * Test OK for {@link MotoController#findMotoByPage(Integer)} with page <br>
	 * haveNext = false
	 */
	@Test
	void testFindByPageOK() {
		Mockito.when(this.service.findMotoByPage(1)).thenReturn(Collections.emptyList());

		final ResponseEntity<Object> response = this.controller.findMotoByPage(1);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertEquals("{count=0, haveNext=false, results=[]}", response.getBody().toString());
	}

	/**
	 * Test OK for {@link MotoController#findMotoByPage(Integer)} with page <br>
	 * haveNext = true
	 */
	@Test
	void testFindByPageOK2() {
		Mockito.when(this.service.findMotoByPage(1)).thenReturn(Collections.emptyList());
		Mockito.when(this.service.countAllMoto()).thenReturn(10000L);

		final ResponseEntity<Object> response = this.controller.findMotoByPage(1);

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertEquals("{count=10000, haveNext=true, results=[]}", response.getBody().toString());
	}

	/**
	 * Test KO for {@link MotoController#findMotoByPage(Integer)} with page
	 */
	@Test
	void testFindByPageKO() {
		Mockito.when(this.service.findMotoByPage(1)).thenThrow(new MBDreamException("Aucune motos trouvée !"));

		final ResponseEntity<Object> response = this.controller.findMotoByPage(1);

		Assertions.assertEquals(404, response.getStatusCode().value());
		Assertions.assertEquals("Aucune motos trouvée !", response.getBody().toString());
	}

	/**
	 * Test OK for {@link MotoController#findMotoBySlug(String)} with slug
	 */
	@Test
	void testFindBySlugOK() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null, null, null);

		Mockito.when(this.service.findMotoBySlug("test")).thenReturn(motoModel);

		final ResponseEntity<Object> response = this.controller.findMotoBySlug("test");

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertEquals(
				"{slugMoto=slug-moto, nomMoto=Moto, backgroundImgMoto=bgc.png, categorie=null, marque=null, descriptionMoto=description, nbMedia=0, idInfo=null}",
				response.getBody().toString());
	}

	/**
	 * Test KO for {@link MotoController#findMotoBySlug(String)} with slug
	 */
	@Test
	void testFindBySlugKO() {
		Mockito.when(this.service.findMotoBySlug("test")).thenThrow(new MBDreamException("Moto introuvable"));

		final ResponseEntity<Object> response = this.controller.findMotoBySlug("test");

		Assertions.assertEquals(404, response.getStatusCode().value());
		Assertions.assertEquals("Moto introuvable", response.getBody());
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
	 * Test OK for {@link MotoController#findFeaturedMoto()}
	 */
	@Test
	void testFeaturedOK() {
		final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null, null, null);

		Mockito.when(this.service.findFeaturedMoto()).thenReturn(Collections.singletonList(motoModel));

		final ResponseEntity<Object> response = this.controller.findFeaturedMoto();

		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertEquals("{results=[{slugMoto=slug-moto, nomMoto=Moto, backgroundImgMoto=bgc.png}]}", response.getBody().toString());
	}

	/**
	 * Test KO for {@link MotoController#findFeaturedMoto()}
	 */
	@Test
	void testFeaturedKO() {
		Mockito.when(this.service.findFeaturedMoto()).thenThrow(new MBDreamException("Aucune featured moto trouvée"));

		final ResponseEntity<Object> response = this.controller.findFeaturedMoto();
		Assertions.assertEquals(404, response.getStatusCode().value());
		Assertions.assertEquals("Aucune featured moto trouvée", response.getBody().toString());
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
