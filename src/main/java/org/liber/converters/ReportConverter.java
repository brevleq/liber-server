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

import org.liber.domain.entities.Report;
import org.liber.service.dto.ReportDTO;

import java.time.ZoneId;

public class ReportConverter {

    public static ReportDTO convert(Report entity) {
        return ReportDTO.builder()
            .id(entity.getId())
            .authorId(entity.getAuthor().getId())
            .authorFirstName(entity.getAuthor().getFirstName())
            .authorLastName(entity.getAuthor().getLastName())
            .content(entity.getContent())
            .createdDate(entity.getCreatedDate().atZone(ZoneId.systemDefault()).toLocalDate())
            .patientId(entity.getPatient().getId())
            .status(entity.getStatus())
            .title(entity.getTitle())
            .type(entity.getType())
            .build();
    }
}
