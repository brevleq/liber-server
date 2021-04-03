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
import org.liber.domain.Scholarity;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.ScholarityService;
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
 * REST controller for managing scholarities.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScholarityResource {

    private final ScholarityService scholarityService;

    /**
     * {@code POST  /scholarities}  : Creates a new scholarity.
     * <p>
     * Creates a new scholarity if not in database yet.
     *
     * @param dto the scholarity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scholarity, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/scholarities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Scholarity> createscholarity(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save scholarity : {}", dto);
        Scholarity createdScholarity = scholarityService.create(dto);
        return ResponseEntity.created(new URI("/api/scholarities/" + createdScholarity.getId()))
            .body(createdScholarity);
    }

    /**
     * {@code GET /scholarities} : get all scholarities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all scholarities.
     */
    @GetMapping("/scholarities")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<Scholarity>> getAllScholaritys(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<Scholarity> page = scholarityService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /scholarities/:id} : get the "id" scholarity.
     *
     * @param id the id of the scholarity to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" scholarity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scholarities/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<Scholarity> getScholarity(@PathVariable Long id) {
        log.debug("REST request to get scholarity: {}", id);
        return ResponseUtil.wrapOrNotFound(
            scholarityService.getScholarityById(id));
    }

    /**
     * {@code DELETE /scholarities/:id} : delete the "id" scholarity.
     *
     * @param id the id of the scholarity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scholarities/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletescholarity(@PathVariable Long id) {
        log.debug("REST request to delete scholarity: {}", id);
        scholarityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
