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

package org.liber.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liber.domain.entities.ReleaseReason;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.ReleaseReasonService;
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
 * REST controller for managing release reasons.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReleaseReasonResource {

    private final ReleaseReasonService releaseReasonService;

    /**
     * {@code POST  /release-reasons}  : Creates a new release reason.
     * <p>
     * Creates a new release reason if not in database yet.
     *
     * @param dto the release reason to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new release reason, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/release-reasons")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<ReleaseReason> createReleaseReason(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save releaseReason : {}", dto);
        ReleaseReason createdReleaseReason = releaseReasonService.create(dto);
        return ResponseEntity.created(new URI("/api/release-reasons/" + createdReleaseReason.getId()))
            .body(createdReleaseReason);
    }

    /**
     * {@code GET /release-reasons} : get all release reasons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all release reasons.
     */
    @GetMapping("/release-reasons")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<ReleaseReason>> getAllReleaseReasons(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<ReleaseReason> page = releaseReasonService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /release-reasons/:id} : get the "id" release reason.
     *
     * @param id the id of the release reason to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" release reason, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/release-reasons/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<ReleaseReason> getReleaseReason(@PathVariable Long id) {
        log.debug("REST request to get releaseReason: {}", id);
        return ResponseUtil.wrapOrNotFound(
            releaseReasonService.getReleaseReasonById(id));
    }

    /**
     * {@code DELETE /release-reasons/:id} : delete the "id" release reason.
     *
     * @param id the id of the release reason to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/release-reasons/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deleteReleaseReason(@PathVariable Long id) {
        log.debug("REST request to delete releaseReason: {}", id);
        releaseReasonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
