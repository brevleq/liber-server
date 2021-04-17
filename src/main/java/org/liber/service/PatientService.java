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
import org.liber.converters.PatientConverter;
import org.liber.domain.entities.*;
import org.liber.domain.repository.*;
import org.liber.service.dto.PatientDTO;
import org.liber.utils.QueryUtils;
import org.liber.web.rest.errors.NotFoundAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final MaritalStatusRepository maritalStatusRepository;
    private final ProfessionRepository professionRepository;
    private final ScholarityRepository scholarityRepository;
    private final PatientRepository patientRepository;
    private final CityRepository cityRepository;

    @Transactional
    public Patient create(PatientDTO dto) {
        Patient entity = load(dto);
        return patientRepository.save(entity);
    }

    private Patient load(PatientDTO dto) {
        return load(dto, new Patient());
    }

    public Patient update(PatientDTO dto) {
        Patient entity=load(dto,dto.getId());
        return patientRepository.save(entity);
    }

    private Patient load(PatientDTO dto, Long patientId){
        Patient entity=patientRepository.findById(patientId).orElseThrow(()->new NotFoundAlertException("A patient with this id was not found", "patientManagement", "notfound"));
        return load(dto,entity);
    }

    private Patient load(PatientDTO dto, Patient entity) {
        entity.setAddressCity(findCity(dto.getAddressCityId()));
        entity.setAddressComplement(dto.getAddressComplement());
        entity.setAddressNeighborhood(dto.getAddressNeighborhood());
        entity.setAddressNumber(dto.getAddressNumber());
        entity.setAddressStreet(dto.getAddressStreet());
        entity.setAddressZip(dto.getAddressZip());
        entity.setBirthDate(dto.getBirthDate());
        entity.setBirthPlace(findCity(dto.getBirthPlaceId()));
        entity.setFatherName(dto.getFatherName());
        entity.setMaritalStatus(findMaritalStatus(dto.getMaritalStatusId()));
        entity.setMotherName(dto.getMotherName());
        entity.setName(dto.getName());
        entity.setProfession(findProfession(dto.getProfessionId()));
        entity.setReceptionDate(dto.getReceptionDate());
        entity.setScholarity(findScholarity(dto.getScholarityId()));
        entity.setSex(dto.getSex());
        entity.setWorking(dto.getWorking());
        return entity;
    }

    private City findCity(Long id) {
        if (id == null)
            return null;
        return cityRepository.findById(id).orElse(null);
    }

    private MaritalStatus findMaritalStatus(Long id) {
        return maritalStatusRepository.findById(id).orElse(null);
    }

    private Profession findProfession(Long id) {
        return professionRepository.findById(id).orElse(null);
    }

    private Scholarity findScholarity(Long id) {
        return scholarityRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<PatientDTO> getAll(String filter, Pageable pageable) {
        filter= QueryUtils.prepareLikeParameter(filter);
        return patientRepository.findAllByFilter(filter,pageable).map(PatientConverter::convert);
    }

    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Long id) {
        return patientRepository.findById(id).map(PatientConverter::convert).orElseThrow(()->new NotFoundAlertException("A patient with this id was not found", "patientManagement", "notfound"));
    }

    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
