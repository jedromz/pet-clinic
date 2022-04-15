package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class UpdateVetCommand {

    @NotNull(message = "FIRSTNAME_NOT_NULL")
    private String firstName;
    @NotNull(message = "LASTNAME_NOT_NULL")
    private String lastName;
    @NotNull(message = "SPECIALIZATION_NOT_NULL")
    private String specialization;
    @NotNull(message = "PET_SPECIALIZATION_NOT_NULL")
    private String petSpecialization;
    @NotNull(message = "RATE_NOT_NULL")
    private BigDecimal rate;
    @NotNull(message = "NIP_NOT_NULL")
    private String nip;
    @NotNull(message = "VERSION_NOT_EMPTY")
    private int version;
    private boolean isFired;
}
