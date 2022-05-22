package org.liber.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class HospitalizationDTO {

    @NotNull
    private final Long patientId;
    @NotNull
    private final Instant startDate;
    private final Instant endDate;
}
