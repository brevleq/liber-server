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

package org.liber.domain.repository;

import org.liber.domain.entities.Hospitalization;
import org.liber.domain.entities.HospitalizationPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface HospitalizationRepository extends JpaRepository<Hospitalization, HospitalizationPK> {

    @Query("select h from Hospitalization h where h.patient.id=:patientId")
    Page<Hospitalization> findAllByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("select h from Hospitalization h where (lower(h.patient.name) like lower(unaccent(cast(:patientName as string)))) and (cast(:startDate as timestamp) is null or h.startDate>=:startDate) and ((cast(:endDate as timestamp) is not null and h.endDate<=:endDate) or (cast(:endDate as timestamp) is null and h.endDate is null))")
    Page<Hospitalization> findAllByFilter(@Param("patientName") String patientName, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate, Pageable pageable);

    /**/
}
