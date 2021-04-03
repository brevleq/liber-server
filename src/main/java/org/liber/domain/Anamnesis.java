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

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

/**
 * A anamnesis.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "anamnesis")
public class Anamnesis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "anamnesis_id_seq")
    @SequenceGenerator(name = "anamnesis_id_seq", sequenceName = "anamnesis_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "overdose", nullable = false)
    private Boolean overdose;

    @NotNull
    @Column(name = "psychotic_break", nullable = false)
    private Boolean psychoticBreak;

    @NotNull
    @Column(name = "drug_abuse_seizure", nullable = false)
    private Boolean drugAbuseSeizure;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "favorite_drug_id", nullable = false)
    private Drug favoriteDrug;

    @NotNull
    @Column(name = "drug_use_frequence", nullable = false)
    private Integer drugUseFrequence;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "housing_condition_id", nullable = false)
    private HousingCondition housingCondition;

    @NotNull
    @Column(name = "quantity_people_in_house", nullable = false)
    private Integer quantityPeopleInHouse;

    @NotNull
    @Column(name = "family_income", nullable = false)
    private BigInteger familyIncome;

    @NotNull
    @Column(name = "in_government_program", nullable = false)
    private Boolean inGovernmentProgram;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "lives_with_kinship_id", nullable = false)
    private Kinship livesWithKinship;

    @OneToMany(mappedBy = "anamnesis")
    private Set<AnamnesisOtherInstitution> anamnesisOtherInstitutions;

    @ManyToMany
    @JoinTable(
        name = "anamnesis_justice_problem",
        joinColumns = @JoinColumn(name = "anamnesis_id"),
        inverseJoinColumns = @JoinColumn(name = "justice_problem_id"))
    private Set<JusticeProblem> justiceProblems;

    @ManyToMany
    @JoinTable(
        name = "anamnesis_drug_used",
        joinColumns = @JoinColumn(name = "anamnesis_id"),
        inverseJoinColumns = @JoinColumn(name = "drug_id"))
    private Set<Drug> drugsUsed;

    @ManyToMany
    @JoinTable(
        name = "anamnesis_controlled_medication",
        joinColumns = @JoinColumn(name = "anamnesis_id"),
        inverseJoinColumns = @JoinColumn(name = "controlled_medication_id"))
    private Set<ControlledMedication> controlledMedications;

    @ManyToMany
    @JoinTable(
        name = "anamnesis_health_problem",
        joinColumns = @JoinColumn(name = "anamnesis_id"),
        inverseJoinColumns = @JoinColumn(name = "health_problem_id"))
    private Set<HealthProblem> healthProblems;

}
