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

package org.liber.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.liber.domain.enums.Sex;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A patient.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_id_seq")
    @SequenceGenerator(name = "patient_id_seq", sequenceName = "patient_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "reception_date", nullable = false)
    private Instant receptionDate;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name = "sex", length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "birth_place_id", nullable = false)
    private City birthPlace;

    @NotNull
    @Size(max = 100)
    @Column(name = "mother_name", length = 100, nullable = false)
    private String motherName;

    @Size(max = 100)
    @Column(name = "father_name", length = 100)
    private String fatherName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "marital_status_id", nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "scholarity_id", nullable = false)
    private Scholarity scholarity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;

    @NotNull
    @Column(name = "working", nullable = false)
    private Boolean working;

    @Size(max = 100)
    @Column(name = "address_street", length = 100)
    private String addressStreet;

    @Size(max = 80)
    @Column(name = "address_neighborhood", length = 80)
    private String addressNeighborhood;

    @Size(max = 6)
    @Column(name = "address_number", length = 6)
    private String addressNumber;

    @Size(max = 30)
    @Column(name = "address_complement", length = 30)
    private String addressComplement;

    @Size(max = 15)
    @Column(name = "address_zip", length = 15)
    private String addressZip;

    @ManyToOne
    @JoinColumn(name = "address_city_id")
    private City addressCity;
}
