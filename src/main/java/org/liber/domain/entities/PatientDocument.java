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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A patient document.
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@IdClass(value = PatientDocumentPK.class)
@Table(name = "patient_document")
public class PatientDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Id
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private DocumentType document;

    @NotNull
    @Size(max = 20)
    @Column(name = "value", length = 20, nullable = false)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDocument that = (PatientDocument) o;
        return Objects.equals(patient.getId(), that.patient.getId()) && Objects.equals(document.getId(), that.document.getId()) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient.getId(), document.getId());
    }


}
