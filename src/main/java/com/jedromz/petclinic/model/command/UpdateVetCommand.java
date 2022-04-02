package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class UpdateVetCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String petSpecialization;
    private BigDecimal rate;
    private String nip;
    private int version;
}
