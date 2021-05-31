package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.MotoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.CategorieModel;
import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.MotoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


/**
 * JUnit test class for {@link MotoService}
 *
 * @author Valentin
 */
@SpringBootTest
class MotoServiceTest {

    private final static MotoModel MOTO_TEST = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", null, null,
            null, null);

    private final static MotoDTO MOTO_DTO = new MotoDTO("slug-dto", "Moto Dto", "description_dto", false, "slug-marque", "slug-cate");

    private final static MarqueModel MARQUE_MODEL = new MarqueModel(1L, "marque", "Marque", null, "description", "logo", null);

    private final static CategorieModel CATEGORIE_MODEL = new CategorieModel(1L, "category", "Categorie", null);

    @MockBean
    private MotoRepository motoRepository;

    @MockBean
    private MarqueService marqueService;

    @MockBean
    CategorieService categorieService;

    @Autowired
    private MotoService service;

    /**
     * Test OK for {@link MotoService#findMotoByPage(Integer)}
     */
    @Test
    void testFindByPageOK() {
        Mockito.when(this.motoRepository.findMotoByPage(10)).thenReturn(Collections.singletonList(MOTO_TEST));

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
    void testFindByPageKO() {
        Mockito.when(this.motoRepository.findMotoByPage(10)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.findMotoByPage(1));
    }

    /**
     * Test OK for {@link MotoService#findFeaturedMoto()}
     */
    @Test
    void testFindFeaturedOK() {
        Mockito.when(this.motoRepository.findFeaturedMoto()).thenReturn(Collections.singletonList(MOTO_TEST));

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
        Mockito.when(this.motoRepository.findFeaturedMoto()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.findFeaturedMoto());
    }

    /**
     * Test OK for {@link MotoService#countAllMoto()}
     */
    @Test
    void testCountAll() {
        Mockito.when(this.motoRepository.count()).thenReturn(5L);

        Assertions.assertEquals(5L, this.service.countAllMoto());
    }

    /**
     * Test OK for {@link MotoService#findMotoBySlug(String)}
     */
    @Test
    void testFindSlugOK() {
        Mockito.when(this.motoRepository.findMotoBySlug(MOTO_TEST.getSlugMoto())).thenReturn(Optional.of(MOTO_TEST));

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

        Mockito.when(this.motoRepository.findMotoBySlug(slug)).thenReturn(Optional.empty());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.findMotoBySlug(slug));
    }

    /**
     * Test KO for {@link MotoService#updateMoto(MotoDTO)}
     */
    @Test
    void testUpdateKOMoto() {
        final String slug = MOTO_DTO.getSlugMoto();

        Mockito.when(this.motoRepository.findMotoBySlug(slug)).thenReturn(Optional.empty());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.updateMoto(MOTO_DTO));
    }

    /**
     * Test KO 2 for {@link MotoService#updateMoto(MotoDTO)}
     */
    @Test
    void testUpdateKOMarque() {
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenThrow(new MBDreamException("Marque introuvable"));

        Assertions.assertThrows(MBDreamException.class, () -> this.service.updateMoto(MOTO_DTO));
    }

    /**
     * Test KO 3 for {@link MotoService#updateMoto(MotoDTO)}
     */
    @Test
    void testUpdateKOCategorie() {
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie())).thenThrow(new MBDreamException("Marque introuvable"));

        Assertions.assertThrows(MBDreamException.class, () -> this.service.updateMoto(MOTO_DTO));
    }

    /**
     * Test KO for {@link MotoService#addMoto(MotoDTO)} <br>
     * Test throw when get slug
     */
    @Test
    void testAddKO() {
        Mockito.when(this.motoRepository.findMotoBySlug("moto-dto")).thenReturn(Optional.of(MOTO_TEST));

        Assertions.assertThrows(MBDreamException.class, () -> this.service.addMoto(MOTO_DTO));
    }

    /**
     * Test KO 2 for {@link MotoService#addMoto(MotoDTO)} <br>
     * Test throw by marque service
     */
    @Test
    void testAddKO2() {
        Mockito.when(this.motoRepository.findMotoBySlug("moto-dto")).thenReturn(Optional.empty());
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenThrow(new MBDreamException("Marque introuvable"));

        Assertions.assertThrows(MBDreamException.class, () -> this.service.addMoto(MOTO_DTO));
    }

    /**
     * Test KO 3 for {@link MotoService#addMoto(MotoDTO)} <br>
     * Test throw by categorie service
     */
    @Test
    void testAddKO3() {
        Mockito.when(this.motoRepository.findMotoBySlug("moto-dto")).thenReturn(Optional.empty());
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenReturn(null);
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie()))
                .thenThrow(new MBDreamException("Categorie introuvable"));

        Assertions.assertThrows(MBDreamException.class, () -> this.service.addMoto(MOTO_DTO));
    }

    /**
     * Test OK for {@link MotoService#addMoto(MotoDTO)}
     */
    @Test
    void testAddOK() {
        Mockito.when(this.motoRepository.findMotoBySlug("moto-dto")).thenReturn(Optional.empty());
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenReturn(null);
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie())).thenReturn(null);
        Mockito.when(this.motoRepository.save(Mockito.any(MotoModel.class))).thenReturn(MOTO_TEST);

        final MotoModel motoGet = this.service.addMoto(MOTO_DTO);

        Assertions.assertNotNull(motoGet);
        Assertions.assertEquals(MOTO_TEST, motoGet);
    }

    /**
     * Test OK for {@link MotoService#updateMoto(MotoDTO)} <br>
     * marque & category null, description different
     */
    @Test
    void testUpdateOK() {
        Mockito.when(this.motoRepository.findMotoBySlug(MOTO_DTO.getSlugMoto())).thenReturn(Optional.of(MOTO_TEST));
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenReturn(null);
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie())).thenReturn(null);
        Mockito.when(this.motoRepository.save(Mockito.any(MotoModel.class))).thenReturn(MOTO_TEST);

        Assertions.assertDoesNotThrow(() -> this.service.updateMoto(MOTO_DTO));
    }

    /**
     * Test OK for {@link MotoService#updateMoto(MotoDTO)} <br>
     * Description same, different marque & category
     */
    @Test
    void testUpdateOK2() {
        final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", MARQUE_MODEL,
                CATEGORIE_MODEL, null, null);

        final MotoDTO motoDTO = new MotoDTO("slug-dto", "Moto Dto", "description", false, "slug-marque", "slug-cate");

        Mockito.when(this.motoRepository.findMotoBySlug(MOTO_DTO.getSlugMoto())).thenReturn(Optional.of(motoModel));
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenReturn(null);
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie())).thenReturn(null);
        Mockito.when(this.motoRepository.save(Mockito.any(MotoModel.class))).thenReturn(MOTO_TEST);

        Assertions.assertDoesNotThrow(() -> this.service.updateMoto(motoDTO));
    }

    /**
     * Test OK for {@link MotoService#updateMoto(MotoDTO)} <br>
     * Description different, marque & category same
     */
    @Test
    void testUpdateOK3() {
        final MotoModel motoModel = new MotoModel(1L, "slug-moto", "Moto", "description", null, false, "bgc.png", MARQUE_MODEL,
                CATEGORIE_MODEL, null, null);

        final MotoDTO motoDTO = new MotoDTO("slug-dto", "Moto Dto", "description_dto", false, MARQUE_MODEL.getSlugMarque(),
                CATEGORIE_MODEL.getSlugCategorie());

        Mockito.when(this.motoRepository.findMotoBySlug(MOTO_DTO.getSlugMoto())).thenReturn(Optional.of(motoModel));
        Mockito.when(this.marqueService.findMarqueBySlug(MOTO_DTO.getSlugMarque())).thenReturn(null);
        Mockito.when(this.categorieService.findCategorieBySlug(MOTO_DTO.getSlugCategorie())).thenReturn(null);
        Mockito.when(this.motoRepository.save(Mockito.any(MotoModel.class))).thenReturn(MOTO_TEST);


        Assertions.assertDoesNotThrow(() -> this.service.updateMoto(motoDTO));
    }

    /**
     * Test KO for {@link MotoService#updateMoto(MotoDTO)} <br>
     * Exception when findMotoBySlug
     */
    @Test
    void testUpdateKO1() {
        Mockito.when(this.motoRepository.findMotoBySlug(MOTO_DTO.getSlugMoto())).thenReturn(Optional.empty());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.updateMoto(MOTO_DTO));
    }

    /**
     * Test KO for {@link MotoService#deleteMoto(String)}
     */
    @Test
    void testDeleteKO() {
        final String slug = MOTO_TEST.getSlugMoto();

        Mockito.when(this.motoRepository.findMotoBySlug(slug)).thenReturn(Optional.empty());

        Assertions.assertThrows(MBDreamException.class, () -> this.service.deleteMoto(slug));
    }

    /**
     * Test OK for {@link MotoService#deleteMoto(String)}
     */
    @Test
    void testDeleteOK() {
        final String slug = MOTO_TEST.getSlugMoto();

        Mockito.when(this.motoRepository.findMotoBySlug(slug)).thenReturn(Optional.of(MOTO_TEST));

        Assertions.assertDoesNotThrow(() -> this.service.deleteMoto(slug));
    }
}
