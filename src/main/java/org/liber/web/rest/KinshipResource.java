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
import org.liber.domain.entities.Kinship;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.KinshipService;
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
 * REST controller for managing kinships.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KinshipResource {

    private final KinshipService kinshipService;

    /**
     * {@code POST  /kinships}  : Creates a new kinship.
     * <p>
     * Creates a new kinship if not in database yet.
     *
     * @param dto the kinship to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kinship, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/kinships")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Kinship> createkinship(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save kinship : {}", dto);
        Kinship createdKinship = kinshipService.create(dto);
        return ResponseEntity.created(new URI("/api/kinships/" + createdKinship.getId()))
            .body(createdKinship);
    }

    /**
     * {@code GET /kinships} : get all kinships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all kinships.
     */
    @GetMapping("/kinships")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<Kinship>> getAllKinships(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<Kinship> page = kinshipService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /kinships/:id} : get the "id" kinship.
     *
     * @param id the id of the kinship to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" kinship, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kinships/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<Kinship> getKinship(@PathVariable Long id) {
        log.debug("REST request to get kinship: {}", id);
        return ResponseUtil.wrapOrNotFound(
            kinshipService.getKinshipById(id));
    }

    /**
     * {@code DELETE /kinships/:id} : delete the "id" kinship.
     *
     * @param id the id of the kinship to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kinships/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletekinship(@PathVariable Long id) {
        log.debug("REST request to delete kinship: {}", id);
        kinshipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
