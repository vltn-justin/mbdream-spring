package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.InfoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.InfoRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    private final InfoRepository infoRepository;
    private final MotoRepository motoRepository;

    @Autowired
    public InfoService(final InfoRepository infoRepository, final MotoRepository motoRepository) {
        this.infoRepository = infoRepository;
        this.motoRepository = motoRepository;
    }

    /**
     * Add info to a moto
     *
     * @param infoDTO Request with all data
     *
     * @return InfoModel or throw MBDreamException if moto not found
     */
    public InfoModel addInfo(final InfoDTO infoDTO) {
        final MotoModel motoModel = this.motoRepository.findMotoBySlug(infoDTO.getSlugMoto()).
                orElseThrow(() -> new MBDreamException("Moto introuvable avec le slug " + infoDTO.getSlugMoto()));

        InfoModel infoModel = new InfoModel();

        this.putDataIntoModel(infoModel, infoDTO);

        infoModel.setMoto(motoModel);

        infoModel = this.infoRepository.save(infoModel);
        motoModel.setInfos(infoModel);
        this.motoRepository.save(motoModel);

        return infoModel;
    }

    /**
     * Method to get info of a moto
     *
     * @param slugMoto Slug of moto
     *
     * @return InfoModel
     */
    public InfoModel getInfoMoto(final String slugMoto) {
        return this.infoRepository.findInfoMoto(slugMoto).orElse(null);
    }

    /**
     * Method to update infos
     *
     * @param infoDTO InfoRequest
     *
     * @return InfoModel
     */
    public InfoModel update(final InfoDTO infoDTO) {
        final InfoModel infoModel = this.getInfoMoto(infoDTO.getSlugMoto());

        this.putDataIntoModel(infoModel, infoDTO);

        return this.infoRepository.save(infoModel);
    }

    /**
     * Method to put data from request into model
     *
     * @param infoModel   InfoModel
     * @param infoDTO InfoRequest
     */
    private void putDataIntoModel(final InfoModel infoModel, final InfoDTO infoDTO) {
        infoModel.setPrix(infoDTO.getPrix());
        infoModel.setArchitectureMoteur(infoDTO.getArchitectureMoteur());
        infoModel.setCylindre(infoDTO.getCylindre());
        infoModel.setPuissance(infoDTO.getPuissance());
        infoModel.setCouple(infoDTO.getCouple());
        infoModel.setPoid(infoDTO.getPoid());
        infoModel.setCapaciteReservoir(infoDTO.getCapaciteReservoir());
        infoModel.setConsommation(infoDTO.getConsommation());
    }
}
