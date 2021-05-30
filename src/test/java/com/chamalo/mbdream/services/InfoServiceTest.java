package com.chamalo.mbdream.services;

import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.repositories.InfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class InfoServiceTest {

	@MockBean
	private InfoRepository repository;

	@Autowired
	private InfoService service;

	/**
	 * Test for OK {@link InfoService#findInfoMoto(String)}
	 */
	@Test
	void testFindInfoOk() {
		final InfoModel infoModel = new InfoModel(1L, 5000, "architecture", 650, 110, 12, 180, 17D, 12.5D, null);

		Mockito.when(this.repository.findInfoMoto("test")).thenReturn(Optional.of(infoModel));

		final InfoModel infoGet = this.service.findInfoMoto("test");

		Assertions.assertNotNull(infoGet);
		Assertions.assertEquals(infoModel.getIdInfo(), infoGet.getIdInfo());
		Assertions.assertEquals(infoModel.getPrix(), infoGet.getPrix());
		Assertions.assertEquals(infoModel.getArchitectureMoteur(), infoGet.getArchitectureMoteur());
		Assertions.assertEquals(infoModel.getCylindre(), infoGet.getCylindre());
		Assertions.assertEquals(infoModel.getPuissance(), infoGet.getPuissance());
		Assertions.assertEquals(infoModel.getCouple(), infoGet.getCouple());
		Assertions.assertEquals(infoModel.getPoid(), infoGet.getPoid());
		Assertions.assertEquals(infoModel.getCapaciteReservoir(), infoGet.getCapaciteReservoir());
		Assertions.assertEquals(infoModel.getConsommation(), infoGet.getConsommation());
		Assertions.assertNull(infoGet.getMoto());
	}

	/**
	 * Test for KO {@link InfoService#findInfoMoto(String)}
	 */
	@Test
	void testFindInfoKO() {
		Mockito.when(this.repository.findInfoMoto("test")).thenReturn(Optional.empty());

		Assertions.assertThrows(MBDreamException.class, () -> this.service.findInfoMoto("test"));
	}
}
