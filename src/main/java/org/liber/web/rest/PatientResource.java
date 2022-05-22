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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liber.domain.entities.Patient;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.PatientService;
import org.liber.service.dto.PatientDTO;
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
import java.util.List;

/**
 * REST controller for managing patients.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PatientResource {

    private final PatientService patientService;

    /**
     * {@code POST  /patients}  : Creates a new patient.
     * <p>
     * Creates a new patient if not in database yet.
     *
     * @param dto the patient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patient, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/patients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO dto) throws URISyntaxException {
        log.debug("REST request to create patient : {}", dto);
        if(dto.getId()!=null)
            throw new BadRequestAlertException("A new patient cannot already have an ID", "patientManagement", "idexists");
        Patient createdPatient = patientService.create(dto);
        return ResponseEntity.created(new URI("/api/patients/" + createdPatient.getId()))
            .body(dto);
    }

    /**
     * {@code PUT  /patients}  : Updates an existing patient.
     * <p>
     * Updates an existing patient.
     *
     * @param dto the patient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patient, or with status {@code 404 (Not Found)} if did not found patient by id.
     * @throws URISyntaxException     if the Location URI syntax is incorrect.
     * @throws NotFoundAlertException {@code 404 (Not Found)} if did not found a patient with dto's id.
     */
    @PutMapping("/patients")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody PatientDTO dto) throws URISyntaxException {
        log.debug("REST request to update patient : {}", dto);
        patientService.update(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * {@code GET /patients} : get all patients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all patients.
     */
    @GetMapping("/patients")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<PatientDTO>> getAllPatients(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<PatientDTO> page = patientService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /patients/:id} : get the "id" patient.
     *
     * @param id the id of the patient to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" patient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patients/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        log.debug("REST request to get patient: {}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    /**
     * {@code DELETE /patients/:id} : delete the "id" patient.
     *
     * @param id the id of the patient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patients/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete patient: {}", id);
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
