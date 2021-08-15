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

package org.liber.domain.repository;

import org.liber.domain.entities.HealthProblem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HealthProblemRepository extends JpaRepository<HealthProblem, Long> {

    @Query("select hp from HealthProblem hp where lower(unaccent(cast(hp.name as string)))=lower(unaccent(cast(:name as string)))")
    Optional<HealthProblem> findByName(@Param("name") String name);

    @Query("select hp from HealthProblem hp where lower(unaccent(cast(hp.name as string))) like lower(unaccent(cast(:filter as string))) ")
    Page<HealthProblem> findAllByName(@Param("filter") String filter, Pageable pageable);

}