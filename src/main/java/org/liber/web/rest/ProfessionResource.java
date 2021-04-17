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
import org.liber.domain.entities.Profession;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.ProfessionService;
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
 * REST controller for managing professions.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfessionResource {

    private final ProfessionService professionService;

    /**
     * {@code POST  /professions}  : Creates a new profession.
     * <p>
     * Creates a new profession if not in database yet.
     *
     * @param dto the profession to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profession, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/professions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Profession> createprofession(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save profession : {}", dto);
        Profession createdProfession = professionService.create(dto);
        return ResponseEntity.created(new URI("/api/professions/" + createdProfession.getId()))
            .body(createdProfession);
    }

    /**
     * {@code GET /professions} : get all professions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all professions.
     */
    @GetMapping("/professions")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<Profession>> getAllProfessions(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<Profession> page = professionService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /professions/:id} : get the "id" profession.
     *
     * @param id the id of the profession to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" profession, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professions/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<Profession> getProfession(@PathVariable Long id) {
        log.debug("REST request to get profession: {}", id);
        return ResponseUtil.wrapOrNotFound(
            professionService.getProfessionById(id));
    }

    /**
     * {@code DELETE /professions/:id} : delete the "id" profession.
     *
     * @param id the id of the profession to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deleteprofession(@PathVariable Long id) {
        log.debug("REST request to delete profession: {}", id);
        professionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
