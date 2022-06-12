/*
 * Copyright (c) 2020 - 2022 Hudson Orsine Assumpção.
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

import org.liber.domain.entities.Hospitalization;
import org.liber.service.dto.HospitalizationDTO;

public class HospitalizationConverter {

    public static HospitalizationDTO convert(Hospitalization entity) {
        HospitalizationDTO dto = HospitalizationDTO.builder()
            .patientId(entity.getPatient().getId())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .name(entity.getPatient().getName())
            .build();
        if (entity.getReleaseReason() != null) {
            dto.setReleaseReasonId(entity.getReleaseReason().getId());
            dto.setReleaseReasonName(entity.getReleaseReason().getName());
        }
        return dto;
    }
}
