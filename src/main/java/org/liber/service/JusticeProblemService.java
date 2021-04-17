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
import org.liber.domain.entities.JusticeProblem;
import org.liber.domain.repository.JusticeProblemRepository;
import org.liber.service.dto.CommonDTO;
import org.liber.utils.QueryUtils;
import org.liber.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JusticeProblemService {

    private final JusticeProblemRepository repository;

    @Transactional
    public JusticeProblem create(CommonDTO dto) {
        Optional<JusticeProblem> found = repository.findByName(dto.getName());
        if (found.isPresent())
            throw new BadRequestAlertException("A justiceProblem already already exists with this name", "justiceProblem", "nameExists");
        JusticeProblem justiceProblem = new JusticeProblem();
        justiceProblem.setName(dto.getName());
        return repository.save(justiceProblem);
    }

    @Transactional(readOnly = true)
    public Page<JusticeProblem> getAll(String filter, Pageable pageable) {
        filter = QueryUtils.prepareLikeParameter(filter);
        return repository.findAllByName(filter, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<JusticeProblem> getJusticeProblemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
