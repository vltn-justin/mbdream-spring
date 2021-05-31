package com.chamalo.mbdream.services;

import com.chamalo.mbdream.dto.InfoDTO;
import com.chamalo.mbdream.exceptions.MBDreamException;
import com.chamalo.mbdream.models.InfoModel;
import com.chamalo.mbdream.repositories.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
	private final InfoRepository infoRepository;
	private final MotoService    motoService;

	@Autowired
	public InfoService(final InfoRepository infoRepository, final MotoService motoService) {
		this.infoRepository = infoRepository;
		this.motoService = motoService;
	}

	/**
	 * Add info to a moto
	 *
	 * @param infoDTO Request with all data
	 */
	public void addInfo(final InfoDTO infoDTO) {
		final var motoModel = this.motoService.findMotoBySlug(infoDTO.getSlugMoto());

		final var infoModel = new InfoModel();

		this.putDataIntoModel(infoModel, infoDTO);

		infoModel.setMoto(motoModel);

		this.infoRepository.save(infoModel);
	}

	/**
	 * Method to get info of a moto
	 *
	 * @param slugMoto Slug of moto
	 * @return InfoModel
	 */
	public InfoModel findInfoMoto(final String slugMoto) {
		return this.infoRepository.findInfoMoto(slugMoto)
				.orElseThrow(() -> new MBDreamException("Impossible de trouver une moto avec ces infos"));
	}

	/**
	 * Method to update infos
	 *
	 * @param infoDTO InfoRequest
	 */
	public void update(final InfoDTO infoDTO) {
		final var infoModel = this.findInfoMoto(infoDTO.getSlugMoto());

		this.putDataIntoModel(infoModel, infoDTO);

		this.infoRepository.save(infoModel);
	}

	/**
	 * Method to put data from request into model
	 *
	 * @param infoModel InfoModel
	 * @param infoDTO   InfoRequest
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
