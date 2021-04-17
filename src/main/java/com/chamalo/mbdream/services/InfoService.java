package com.chamalo.mbdream.services;

import com.chamalo.mbdream.DTO.InfoRequest;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.models.MotoModel;
import com.chamalo.mbdream.repositories.InfoRepository;
import com.chamalo.mbdream.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * @param infoRequest Request with all data
     *
     * @return InfoModel or throw MBDreamException if moto not found
     */
    public InfoModel addInfo(final InfoRequest infoRequest) {
        final Optional<MotoModel> motoModel = this.motoRepository.findMotoBySlug(infoRequest.getSlugMoto());

        motoModel.orElseThrow(() -> new MBDreamException("Moto introuvable avec le slug " + infoRequest.getSlugMoto()));

        InfoModel infoModel = new InfoModel();

        infoModel.setMoto(motoModel.get());
        infoModel.setPrix(infoRequest.getPrix());
        infoModel.setArchitectureMoteur(infoRequest.getArchitectureMoteur());
        infoModel.setCylindre(infoRequest.getCylindre());
        infoModel.setPuissance(infoRequest.getPuissance());
        infoModel.setCouple(infoRequest.getCouple());
        infoModel.setPoid(infoRequest.getPoid());
        infoModel.setCapaciteReservoir(infoRequest.getCapaciteReservoir());
        infoModel.setConsommation(infoRequest.getConsommation());

        infoModel = this.infoRepository.save(infoModel);
        motoModel.get().setInfos(infoModel);
        this.motoRepository.save(motoModel.get());

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
}
