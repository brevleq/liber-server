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
import java.io.Serializable;
import java.math.BigInteger;

/**
 * A anamnesis.
 */
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "anamnesis")
public class Anamnesis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @SequenceGenerator(name = "anamnesis_id_seq", sequenceName = "anamnesis_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "patient_id", nullable = false)
    private String patient_id; //todo

    @NotNull
    @Column(name = "overdose", nullable = false)
    private Boolean overdose;

    @NotNull
    @Column(name = "psychotic_break", nullable = false)
    private Boolean psychotic_break;

    @NotNull
    @Column(name = "drug_abuse_seizure", nullable = false)
    private Boolean drug_abuse_seizure;

    @NotNull
    @Column(name = "favorite_drug_id", nullable = false)
    private String favorite_drug_id; //todo

    @NotNull
    @Column(name = "drug_use_frequence", nullable = false)
    private Integer drug_use_frequence;

    @NotNull
    @Column(name = "housing_condition_id", nullable = false)
    private String housing_condition_id; //todo

    @NotNull
    @Column(name = "quantity_people_in_house", nullable = false)
    private Integer quantity_people_in_house;

    @NotNull
    @Column(name = "family_income", nullable = false)
    private BigInteger family_income;

    @NotNull
    @Column(name = "in_government_program", nullable = false)
    private Boolean in_government_program;

    @NotNull
    @Column(name = "lives_with_kinship_id", nullable = false)
    private String lives_with_kinship_id; //todo

}
