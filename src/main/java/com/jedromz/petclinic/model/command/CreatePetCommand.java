package com.jedromz.petclinic.model.command;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePetCommand {
    @NotNull(message = "PET_NAME_NOT_NULL")
    private String petName;
    @NotNull(message = "TYPE_NOT_NULL")
    private String type;
    @NotNull(message = "RACE_NOT_NULL")
    private String race;
    @NotNull(message = "BIRTHDATE_NOT_NULL")
    @Past
    private LocalDate birthDate;
    @NotNull(message = "OWNER_NAME_NOT_NULL")
    private String ownerName;
    @NotNull(message = "OWNER_EMAIL_NOT_NULL")
    @Email
    private String ownerEmail;
}
