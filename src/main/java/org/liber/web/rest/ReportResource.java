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
import org.liber.domain.entities.Report;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.ReportService;
import org.liber.service.dto.ReportDTO;
import org.liber.web.rest.errors.BadRequestAlertException;
import org.liber.web.rest.errors.NotFoundAlertException;
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
 * REST controller for managing reports.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportResource {

    private final ReportService reportService;

    /**
     * {@code POST  /reports}  : Creates a new report.
     * <p>
     * Creates a new report if not in database yet.
     *
     * @param dto the report to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new report, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/reports")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.DENTIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<ReportDTO> createReport(@Valid @RequestBody ReportDTO dto) throws URISyntaxException {
        log.debug("REST request to create report : {}", dto);
        if (dto.getId() != null)
            throw new BadRequestAlertException("A new report cannot already have an ID", "reportManagement", "idexists");
        Report createdReport = reportService.create(dto);
        return ResponseEntity.created(new URI("/api/reports/" + createdReport.getId()))
            .body(dto);
    }

    /**
     * {@code POST  /reports}  : Updates an existing report.
     * <p>
     * Updates an existing report.
     *
     * @param dto the report to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated report, or with status {@code 404 (Not Found)} if did not found report by id.
     * @throws URISyntaxException     if the Location URI syntax is incorrect.
     * @throws NotFoundAlertException {@code 404 (Not Found)} if did not found a report with dto's id.
     */
    @PutMapping("/reports")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.DENTIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<ReportDTO> updateReport(@Valid @RequestBody ReportDTO dto) throws URISyntaxException {
        log.debug("REST request to update report : {}", dto);
        reportService.update(dto);
        return ResponseEntity.ok(dto);
    }

    /**
     * {@code GET /reports} : get all reports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all reports.
     */
    @GetMapping("/reports")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.DENTIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<List<ReportDTO>> getAllReports(@RequestParam Long patientId, Pageable pageable) {
        final Page<ReportDTO> page = reportService.getAll(patientId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /reports/:id} : get the "id" report.
     *
     * @param id the id of the report to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" report, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reports/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<ReportDTO> getReport(@PathVariable Long id) {
        log.debug("REST request to get report: {}", id);
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    /**
     * {@code DELETE /reports/:id} : delete the "id" report.
     *
     * @param id the id of the report to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reports/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        log.debug("REST request to delete report: {}", id);
        reportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
