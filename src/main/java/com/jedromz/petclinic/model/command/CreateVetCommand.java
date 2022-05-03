package com.jedromz.petclinic.model.command;

import com.jedromz.petclinic.validation.annotation.UniqueNip;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVetCommand {
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
    @UniqueNip
    @Size(min = 11,max = 11)
    private String nip;
}
