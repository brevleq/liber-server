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

package org.liber.service;

import lombok.RequiredArgsConstructor;
import org.liber.converters.ReportConverter;
import org.liber.domain.entities.Patient;
import org.liber.domain.entities.Report;
import org.liber.domain.entities.User;
import org.liber.domain.repository.PatientRepository;
import org.liber.domain.repository.ReportRepository;
import org.liber.domain.repository.UserRepository;
import org.liber.security.AuthoritiesConstants;
import org.liber.security.SecurityUtils;
import org.liber.service.dto.ReportDTO;
import org.liber.service.errors.BadRequestAlertException;
import org.liber.service.errors.NotFoundAlertException;
import org.liber.service.errors.UnauthorizedAlertException;
import org.liber.utils.SanitizeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Transactional
    public Report create(ReportDTO dto) {
        if (dto.getId() != null)
            throw new BadRequestAlertException("It can't create a report that already has an id", "report", "hasId");
        return loadAndSave(new Report(), dto);
    }

    @Transactional
    public Report update(ReportDTO dto) {
        Optional<Report> found = reportRepository.findById(dto.getId());
        if (!found.isPresent())
            throw new NotFoundAlertException("No report found with provided ID", "report", "reportNotFound");
        Report report = found.get();
        if (!isOwner(report))
            throw new UnauthorizedAlertException("Unauthorized", "report", "unauthorized");
        return loadAndSave(report, dto);
    }

    private boolean isOwner(Report report) {
        String login = SecurityUtils.getCurrentUserLogin().get();
        return report.getAuthor().getLogin().equals(login);
    }

    private Report loadAndSave(Report entity, ReportDTO dto) {
        entity.setAuthor(findCurrentUser());
        entity.setContent(SanitizeUtils.sanitizeContent(dto.getContent()));
        entity.setPatient(findPatient(dto.getPatientId()));
        entity.setTitle(dto.getTitle());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        return reportRepository.save(entity);
    }

    private User findCurrentUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get())
            .orElseThrow(() -> new BadRequestAlertException("User not found", "report", "authorNotFound"));
    }

    private Patient findPatient(Long patientId) {
        return patientRepository.findById(patientId)
            .orElseThrow(() -> new BadRequestAlertException("Patient not found", "report", "patientNotFound"));
    }

    @Transactional(readOnly = true)
    public Page<ReportDTO> getAll(Long patientId, Pageable pageable) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DENTIST, AuthoritiesConstants.PSYCHIATRIST, AuthoritiesConstants.PSYCHOLOGIST, AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "report", "unauthorized");
        return reportRepository.findAllByPatientId(patientId, pageable).map(ReportConverter::convert);
    }

    @Transactional(readOnly = true)
    public ReportDTO getReportById(Long id) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DENTIST, AuthoritiesConstants.PSYCHIATRIST, AuthoritiesConstants.PSYCHOLOGIST, AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "report", "unauthorized");
        return reportRepository.findById(id).map(ReportConverter::convert).orElseThrow(() -> new NotFoundAlertException("Report not found", "report", "reportNotFound"));
    }

    @Transactional
    public void delete(Long id) {
        Optional<Report> found = reportRepository.findById(id);
        if (!found.isPresent())
            throw new NotFoundAlertException("No report found with provided ID", "report", "reportNotFound");
        Report entity = found.get();
        User user = findCurrentUser();
        if (user == null || !user.equals(entity))
            throw new UnauthorizedAlertException("Unauthorized", "report", "unauthorized");
        reportRepository.delete(entity);
    }
}
