/*
 * Copyright (c) 2020 - 2021 Hudson Orsine Assumpção.
 *
 * This file is part of Liber Server.
 *
 * Liber Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later
 * version.
 *
 * Liber Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Liber Server.  If not, see <https://www.gnu.org/licenses/>
 */

package org.liber.converters;

import org.liber.domain.entities.Patient;
import org.liber.domain.entities.PatientDocument;
import org.liber.service.dto.PatientDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PatientConverter {

    public static PatientDTO convert(Patient entity) {
        return PatientDTO.builder()
            .id(entity.getId())
            .addressCityId(entity.getAddressCity().getId())
            .addressComplement(entity.getAddressComplement())
            .addressNeighborhood(entity.getAddressNeighborhood())
            .addressNumber(entity.getAddressNumber())
            .addressStreet(entity.getAddressStreet())
            .addressZip(entity.getAddressZip())
            .birthDate(entity.getBirthDate())
            .birthPlaceId(entity.getBirthPlace().getId())
            .fatherName(entity.getFatherName())
            .maritalStatusId(entity.getMaritalStatus().getId())
            .motherName(entity.getMotherName())
            .name(entity.getName())
            .professionId(entity.getProfession().getId())
            .receptionDate(entity.getReceptionDate())
            .scholarityId(entity.getScholarity().getId())
            .sex(entity.getSex())
            .working(entity.getWorking())
            .documents(convertDocuments(entity.getDocuments()))
            .build();
    }

    private static Map<Long, String> convertDocuments(Set<PatientDocument> patientDocuments) {
        final Map<Long, String> documents = new HashMap<>();
        for (PatientDocument patientDocument : patientDocuments)
            documents.put(patientDocument.getDocument().getId(), patientDocument.getValue());
        return documents;
    }
}
