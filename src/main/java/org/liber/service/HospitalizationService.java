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

package org.liber.service;

import lombok.RequiredArgsConstructor;
import org.liber.domain.entities.Hospitalization;
import org.liber.domain.entities.HospitalizationPK;
import org.liber.domain.entities.Patient;
import org.liber.domain.repository.HospitalizationRepository;
import org.liber.domain.repository.PatientRepository;
import org.liber.security.AuthoritiesConstants;
import org.liber.security.SecurityUtils;
import org.liber.service.dto.HospitalizationDTO;
import org.liber.service.errors.BadRequestAlertException;
import org.liber.service.errors.NotFoundAlertException;
import org.liber.service.errors.UnauthorizedAlertException;
import org.liber.utils.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalizationService {

    private final HospitalizationRepository hospitalizationRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Hospitalization create(HospitalizationDTO dto) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "hospitalization", "unauthorized");
        Patient patient = patientRepository.findById(dto.getPatientId())
            .orElseThrow(() -> new BadRequestAlertException("Patient not found", "hospitalization", "patientNotFound"));
        if (hospitalizationRepository.findCurrentByPatientId(patient.getId()) != null)
            throw new BadRequestAlertException("Patient already hospitalized", "hospitalization", "alreadyHospitalized");
        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setPatient(patient);
        hospitalization.setStartDate(dto.getStartDate());
        return hospitalizationRepository.save(hospitalization);
    }

    @Transactional
    public Hospitalization finish(HospitalizationDTO dto) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "hospitalization", "unauthorized");
        if (dto.getEndDate() == null)
            throw new BadRequestAlertException("You need a end date to finish an hospitalization", "hospitalization", "endDateRequired");
        Hospitalization hospitalization = findHospitalization(dto);
        hospitalization.setEndDate(dto.getEndDate());
        return hospitalizationRepository.save(hospitalization);
    }

    @Transactional(readOnly = true)
    public Page<HospitalizationDTO> getAll(Long patientId, Pageable pageable) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DENTIST, AuthoritiesConstants.PSYCHIATRIST, AuthoritiesConstants.PSYCHOLOGIST, AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "hospitalization", "unauthorized");
        return hospitalizationRepository.findAllByPatientId(patientId, pageable).map(h -> new HospitalizationDTO(patientId, h.getStartDate(), h.getEndDate(), h.getPatient().getName()));
    }

    public Page<HospitalizationDTO> getAll(String patientName, Instant startDate, Instant endDate, Pageable pageable) {
        patientName = QueryUtils.prepareLikeParameter(patientName);
        return hospitalizationRepository.findAllByFilter(patientName, startDate, endDate, pageable).map(h -> new HospitalizationDTO(h.getPatient().getId(), h.getStartDate(), h.getEndDate(), h.getPatient().getName()));
    }

    @Transactional
    public void delete(HospitalizationDTO dto) {
        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SOCIAL_ASSISTANT))
            throw new UnauthorizedAlertException("Unauthorized", "hospitalization", "unauthorized");
        Hospitalization entity = findHospitalization(dto);
        hospitalizationRepository.delete(entity);
    }

    private Hospitalization findHospitalization(HospitalizationDTO dto) {
        HospitalizationPK pk = new HospitalizationPK(dto.getPatientId(), dto.getStartDate());
        Optional<Hospitalization> found = hospitalizationRepository.findById(pk);
        if (!found.isPresent())
            throw new NotFoundAlertException("No hospitalization found with provided patient ID and start date", "hospitalization", "hospitalizationNotFound");
        return found.get();
    }

    public boolean isHospitalized(Long patientId) {
        return hospitalizationRepository.findCurrentByPatientId(patientId) != null;
    }
}
