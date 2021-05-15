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

package org.liber.service;

import lombok.RequiredArgsConstructor;
import org.liber.domain.entities.City;
import org.liber.domain.repository.CityRepository;
import org.liber.service.dto.CommonDTO;
import org.liber.utils.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    @Transactional(readOnly = true)
    public Page<CommonDTO> getAll(String filter, Pageable pageable) {
        filter = QueryUtils.prepareLikeParameter(filter);
        return repository.findAllByName(filter, pageable).map(this::convert);
    }

    @Transactional(readOnly = true)
    public Optional<CommonDTO> getCityById(Long id) {
        return repository.findById(id).map(this::convert);
    }

    private CommonDTO convert(City entity) {
        StringBuilder name = new StringBuilder(entity.getName())
            .append(", ")
            .append(entity.getState().getAbbreviation())
            .append(" - ")
            .append(entity.getState().getCountry().getName());
        return CommonDTO.builder()
            .id(entity.getId())
            .name(name.toString())
            .build();
    }
}
