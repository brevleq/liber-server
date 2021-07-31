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

package org.liber.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liber.domain.entities.HealthProblem;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.HealthProblemService;
import org.liber.service.dto.CommonDTO;
import org.liber.service.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing healthProblems.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthProblemResource {

    private final HealthProblemService healthProblemService;

    /**
     * {@code POST  /health-problems}  : Creates a new healthProblem.
     * <p>
     * Creates a new healthProblem if not in database yet.
     *
     * @param dto the healthProblem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthProblem, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/health-problems")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<HealthProblem> createhealthProblem(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save healthProblem : {}", dto);
        HealthProblem createdHealthProblem = healthProblemService.create(dto);
        return ResponseEntity.created(new URI("/api/health-problems/" + createdHealthProblem.getId()))
            .body(createdHealthProblem);
    }

    /**
     * {@code GET /health-problems} : get all healthProblems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all healthProblems.
     */
    @GetMapping("/health-problems")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<HealthProblem>> getAllHealthProblems(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<HealthProblem> page = healthProblemService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /health-problems/:id} : get the "id" healthProblem.
     *
     * @param id the id of the healthProblem to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" healthProblem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-problems/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<HealthProblem> getHealthProblem(@PathVariable Long id) {
        log.debug("REST request to get healthProblem: {}", id);
        return ResponseUtil.wrapOrNotFound(
            healthProblemService.getHealthProblemById(id));
    }

    /**
     * {@code DELETE /health-problems/:id} : delete the "id" healthProblem.
     *
     * @param id the id of the healthProblem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-problems/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletehealthProblem(@PathVariable Long id) {
        log.debug("REST request to delete healthProblem: {}", id);
        healthProblemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
