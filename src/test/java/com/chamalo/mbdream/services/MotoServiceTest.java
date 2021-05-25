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
class MotoServiceTest {

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

		Assertions.assertEquals(MOTO_TEST, motoFound);
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
	 * Test OK for {@link MotoService#findFeaturedMoto()}
	 */
	@Test
	void testFindFeaturedOK() {
		when(this.motoRepository.findFeaturedMoto()).thenReturn(Collections.singletonList(MOTO_TEST));

		final Collection<MotoModel> motoModels = this.service.findFeaturedMoto();

		Assertions.assertNotNull(motoModels);
		Assertions.assertEquals(1, motoModels.size());

		final MotoModel motoFound = motoModels.iterator().next();

		Assertions.assertEquals(MOTO_TEST, motoFound);
	}

	/**
	 * Test KO for {@link MotoService#findFeaturedMoto()}
	 */
	@Test
	void testFindFeaturedKO() {
		when(this.motoRepository.findFeaturedMoto()).thenReturn(Collections.emptyList());

		Assertions.assertThrows(MBDreamException.class, () -> this.service.findFeaturedMoto());
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
		Assertions.assertEquals(MOTO_TEST, motoFound);
	}

	/**
	 * Test KO for {@link MotoService#findMotoBySlug(String)}
	 */
	@Test
	void testFindSlugKO() {
		// Sonar n'aime pas MOTO_TEST.getSlugMoto() dans la lambda
		final String slug = MOTO_TEST.getSlugMoto();

		when(this.motoRepository.findMotoBySlug(slug)).thenReturn(Optional.empty());

		Assertions.assertThrows(MBDreamException.class, () -> this.service.findMotoBySlug(slug));
	}
}
