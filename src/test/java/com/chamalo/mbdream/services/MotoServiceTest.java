package com.chamalo.mbdream.services;

import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MotoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * JUnit test class for {@link MotoService}
 *
 * @author Valentin
 */
@SpringBootTest
public class MotoServiceTest {

	private final static MotoModel MOTO_TEST = new MotoModel(1L, "slug_moto", "Moto", "description", null, false, "bgc.png", null, null,
			null, null);

	@MockBean
	private MotoRepository motoRepository;

	@Autowired
	private MotoService service;

	/**
	 * Test OK for {@link MotoService#findMotoByPage(Integer)}
	 */
	@Test
	void testFindAllByPageOK() {
		when(this.motoRepository.findMotoByPage(10)).thenReturn(Collections.singletonList(MOTO_TEST));

		final Collection<MotoModel> motoModelCollection = this.service.findMotoByPage(1);

		Assertions.assertNotNull(motoModelCollection);
		Assertions.assertEquals(1, motoModelCollection.size());

		final MotoModel motoFound = motoModelCollection.iterator().next();

		Assertions.assertEquals(motoFound, MOTO_TEST);
	}

	/**
	 * Test KO for {@link MotoService#findMotoByPage(Integer)}
	 */
	@Test
	void testFindAllByPageKO() {
		when(this.motoRepository.findMotoByPage(10)).thenReturn(Collections.emptyList());

		Assertions.assertThrows(MBDreamException.class, () -> this.service.findMotoByPage(1));
	}

	/**
	 * Test OK for {@link MotoService#countAllMoto()}
	 */
	@Test
	void testCountAll() {
		when(this.motoRepository.count()).thenReturn(5L);

		Assertions.assertEquals(5L, this.service.countAllMoto());
	}

	/**
	 * Test OK for {@link MotoService#findMotoBySlug(String)}
	 */
	@Test
	void testFindSlugOK() {
		when(this.motoRepository.findMotoBySlug(MOTO_TEST.getSlugMoto())).thenReturn(Optional.of(MOTO_TEST));

		final MotoModel motoFound = this.service.findMotoBySlug(MOTO_TEST.getSlugMoto());

		Assertions.assertNotNull(motoFound);
		Assertions.assertEquals(motoFound, MOTO_TEST);
	}

	/**
	 * Test KO for {@link MotoService#findMotoBySlug(String)}
	 */
	@Test
	void testFindSlugKO() {
		when(this.motoRepository.findMotoBySlug(MOTO_TEST.getSlugMoto())).thenReturn(Optional.empty());

		Assertions.assertThrows(MBDreamException.class, () -> this.service.findMotoBySlug(MOTO_TEST.getSlugMoto()));
	}

	/**
	 * Test OK for {@link MotoService#deleteMoto(String)}
	 */
	@Test
	void testDeleteOK() {
		when(this.motoRepository.findMotoBySlug("slug_moto")).thenReturn(Optional.of(MOTO_TEST));
	}
}
