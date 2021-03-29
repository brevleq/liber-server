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

package org.liber.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A companion.
 */
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "companion")
public class Companion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @SequenceGenerator(name = "companion_id_seq", sequenceName = "companion_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "address_street", length = 100, nullable = false)
    private String address_street;

    @NotNull
    @Size(max = 80)
    @Column(name = "address_neighborhood", length = 80, nullable = false)
    private String address_neighborhood;

    @NotNull
    @Size(max = 6)
    @Column(name = "address_number", length = 6, nullable = false)
    private String address_number;

    @NotNull
    @Size(max = 30)
    @Column(name = "address_complement", length = 30, nullable = false)
    private String address_complement;

    @NotNull
    @Size(max = 15)
    @Column(name = "address_zip", length = 15, nullable = false)
    private String address_zip;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_city_id", nullable = false)
    private City addressCity;

    @NotNull
    @Size(max = 15)
    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "kinship_id", nullable = false)
    private Kinship kinship;

}
