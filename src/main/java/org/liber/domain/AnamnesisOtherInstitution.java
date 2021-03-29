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

/**
 * A anamnesis other institution.
 */
@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "anamnesis_other_institution")
public class AnamnesisOtherInstitution implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AnamnesisOtherInstitutionPK id;


    @ManyToOne
    @MapsId("anamnesisId")
    @JoinColumn(name = "anamnesis_id")
    private Anamnesis anamnesis;

    @ManyToOne
    @MapsId("otherInstitutionId")
    @JoinColumn(name = "other_institution_id")
    private OtherInstitution otherInstitution;

    @NotNull
    @Column(name = "period_in_days", nullable = false)
    private Integer periodInDays;

}
