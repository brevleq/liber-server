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
import java.util.List;

/**
 * A state.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 2)
    @Column(name = "abbreviation", length = 2, nullable = false)
    private String abbreviation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "state")
    private List<City> cities;

}
