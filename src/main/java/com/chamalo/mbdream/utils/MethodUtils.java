package com.chamalo.mbdream.utils;

import com.chamalo.mbdream.models.MarqueModel;
import com.chamalo.mbdream.models.MotoModel;
import lombok.NoArgsConstructor;

import java.util.*;

public class MethodUtils {

    /**
     * Method to extract data from motos
     *
     * @param motos Collection of MotoModel
     *
     * @return List with extracted data
     */
    public static List<Map<String, Object>> extractMoto (Collection<MotoModel> motos) {
        if (motos != null) {
            List<Map<String, Object>> listMoto = new ArrayList<>();
            for (MotoModel m : motos) {
                Map<String, Object> motoMap = new LinkedHashMap<>();
                motoMap.put("idMoto", m.getIdMoto());
                motoMap.put("slugMoto", m.getSlugMoto());
                motoMap.put("nomMoto", m.getNomMoto());
                listMoto.add(motoMap);
            }
            return listMoto;
        }

        return null;
    }
}
