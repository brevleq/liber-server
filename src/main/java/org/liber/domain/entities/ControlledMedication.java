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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A controlled medication.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "controlled_medication")
public class ControlledMedication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "controlled_medication_id_seq")
    @SequenceGenerator(name = "controlled_medication_id_seq", sequenceName = "controlled_medication_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    private void toLowerCase() {
        this.name = this.name.toLowerCase();
    }

}
