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
import org.liber.domain.OtherInstitution;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.OtherInstitutionService;
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
 * REST controller for managing otherInstitutions.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OtherInstitutionResource {

    private final OtherInstitutionService otherInstitutionService;

    /**
     * {@code POST  /other-institutions}  : Creates a new otherInstitution.
     * <p>
     * Creates a new otherInstitution if not in database yet.
     *
     * @param dto the otherInstitution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otherInstitution, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/other-institutions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<OtherInstitution> createotherInstitution(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save otherInstitution : {}", dto);
        OtherInstitution createdOtherInstitution = otherInstitutionService.create(dto);
        return ResponseEntity.created(new URI("/api/other-institutions/" + createdOtherInstitution.getId()))
            .body(createdOtherInstitution);
    }

    /**
     * {@code GET /other-institutions} : get all otherInstitutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all otherInstitutions.
     */
    @GetMapping("/other-institutions")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<OtherInstitution>> getAllOtherInstitutions(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<OtherInstitution> page = otherInstitutionService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /other-institutions/:id} : get the "id" otherInstitution.
     *
     * @param id the id of the otherInstitution to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" otherInstitution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/other-institutions/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<OtherInstitution> getOtherInstitution(@PathVariable Long id) {
        log.debug("REST request to get otherInstitution: {}", id);
        return ResponseUtil.wrapOrNotFound(
            otherInstitutionService.getOtherInstitutionById(id));
    }

    /**
     * {@code DELETE /other-institutions/:id} : delete the "id" otherInstitution.
     *
     * @param id the id of the otherInstitution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/other-institutions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deleteotherInstitution(@PathVariable Long id) {
        log.debug("REST request to delete otherInstitution: {}", id);
        otherInstitutionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
