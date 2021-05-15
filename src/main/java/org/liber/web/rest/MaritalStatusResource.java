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
import org.liber.domain.entities.MaritalStatus;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.MaritalStatusService;
import org.liber.service.dto.CommonDTO;
import org.liber.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing maritalStatuss.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MaritalStatusResource {

    private final MaritalStatusService maritalStatusService;

    /**
     * {@code POST  /marital-status}  : Creates a new maritalStatus.
     * <p>
     * Creates a new maritalStatus if not in database yet.
     *
     * @param dto the maritalStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new maritalStatus, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/marital-status")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<MaritalStatus> createMaritalStatus(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save maritalStatus : {}", dto);
        MaritalStatus createdMaritalStatus = maritalStatusService.create(dto);
        return ResponseEntity.created(new URI("/api/marital-status/" + createdMaritalStatus.getId()))
            .body(createdMaritalStatus);
    }

    /**
     * {@code GET /marital-status} : get all maritalStatuss.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all maritalStatuss.
     */
    @GetMapping("/marital-status")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<MaritalStatus>> getAllMaritalStatuss(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<MaritalStatus> page = maritalStatusService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /marital-status/:id} : get the "id" maritalStatus.
     *
     * @param id the id of the maritalStatus to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" maritalStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/marital-status/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<MaritalStatus> getMaritalStatus(@PathVariable Long id) {
        log.debug("REST request to get maritalStatus: {}", id);
        return ResponseUtil.wrapOrNotFound(
            maritalStatusService.getMaritalStatusById(id));
    }

    /**
     * {@code DELETE /marital-status/:id} : delete the "id" maritalStatus.
     *
     * @param id the id of the maritalStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/marital-status/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletemaritalStatus(@PathVariable Long id) {
        log.debug("REST request to delete maritalStatus: {}", id);
        maritalStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
