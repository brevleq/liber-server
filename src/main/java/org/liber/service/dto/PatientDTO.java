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

package org.liber.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.liber.domain.enums.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long id;

    @Size(max = 100)
    private String name;

    private Instant receptionDate;

    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @NotNull
    private Sex sex;

    @NotNull
    private Long birthPlaceId;

    @NotNull
    @Size(max = 100)
    private String motherName;

    @Size(max = 100)
    private String fatherName;

    @NotNull
    private Long maritalStatusId;

    @NotNull
    private Long scholarityId;

    @NotNull
    private Long professionId;

    @NotNull
    private boolean working;

    @Size(max = 100)
    private String addressStreet;

    @Size(max = 80)
    private String addressNeighborhood;

    @Size(max = 6)
    private String addressNumber;

    @Size(max = 30)
    private String addressComplement;

    @Size(max = 15)
    private String addressZip;

    private Long addressCityId;
}
