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

package org.liber.repository;

import org.liber.domain.ControlledMedication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ControlledMedicationRepository extends JpaRepository<ControlledMedication, Long> {

    @Query("select cm from ControlledMedication cm where lower(unaccent(cm.name))=lower(unaccent(:name))")
    Optional<ControlledMedication> findByName(@Param("name") String name);

    @Query("select cm from ControlledMedication cm where lower(unaccent(cm.name)) like lower(unaccent(:filter)) ")
    Page<ControlledMedication> findAllByName(@Param("filter") String filter, Pageable pageable);
}
