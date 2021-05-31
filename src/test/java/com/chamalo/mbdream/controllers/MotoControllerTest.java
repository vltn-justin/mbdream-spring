package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.exceptions.MBDreamException;
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

	@MockBean
	private MotoService service;

	@Autowired
	private MotoController controller;

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
}
