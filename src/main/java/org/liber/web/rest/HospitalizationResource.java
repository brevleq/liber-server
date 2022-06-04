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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.HospitalizationService;
import org.liber.service.dto.HospitalizationDTO;
import org.liber.service.errors.BadRequestAlertException;
import org.liber.service.errors.NotFoundAlertException;
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
import java.time.Instant;
import java.util.List;

/**
 * REST controller for managing hospitalization.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HospitalizationResource {

    private final HospitalizationService hospitalizationService;

    /**
     * {@code POST  /hospitalizations}  : Creates a new hospitalization.
     * <p>
     * Creates a new hospitalization for patient if not in database yet.
     *
     * @param dto the hospitalization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalization, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/hospitalizations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<HospitalizationDTO> createHospitalization(@Valid @RequestBody HospitalizationDTO dto) throws URISyntaxException {
        log.debug("REST request to save hospitalization : {}", dto);
        hospitalizationService.create(dto);
        return ResponseEntity.created(new URI("/api/hospitalizations/"))
            .body(dto);
    }

    /**
     * {@code PUT  /hospitalizations}  : Finishes an existing hospitalization.
     * <p>
     * Finishes an existing hospitalization.
     *
     * @param dto the hospitalization to finish.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalization, or with status {@code 404 (Not Found)} if did not find hospitalization.
     * @throws NotFoundAlertException {@code 404 (Not Found)} if did not find the hospitalization.
     */
    @PutMapping("/hospitalizations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<HospitalizationDTO> finishHospitalization(@Valid @RequestBody HospitalizationDTO dto) {
        log.debug("REST request to finish a hospitalization : {}", dto);
        hospitalizationService.finish(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * {@code GET /hospitalizations} : get all hospitalizations by filter.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all hospitalizations for a patient.
     */
    @GetMapping("/hospitalizations")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<HospitalizationDTO>> getAllHospitalizations(@RequestParam(required = false) String patientName,
                                                                           @RequestParam(required = false) Instant startDate,
                                                                           @RequestParam(required = false) Instant endDate,
                                                                           Pageable pageable) {
        final Page<HospitalizationDTO> page = hospitalizationService.getAll(patientName, startDate, endDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /hospitalizations/:patientId} : get all hospitalizations for a patient.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all hospitalizations for a patient.
     */
    @GetMapping("/hospitalizations/{patientId}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<HospitalizationDTO>> getAllHospitalizationsForPatient(@PathVariable Long patientId, Pageable pageable) {
        final Page<HospitalizationDTO> page = hospitalizationService.getAll(patientId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code DELETE /hospitalizations} : delete a hospitalization.
     *
     * @param dto the hospitalization data to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitalizations")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deleteHospitalization(@Valid @RequestBody HospitalizationDTO dto) {
        log.debug("REST request to delete hospitalization: {}", dto);
        hospitalizationService.delete(dto);
        return ResponseEntity.noContent().build();
    }
}
