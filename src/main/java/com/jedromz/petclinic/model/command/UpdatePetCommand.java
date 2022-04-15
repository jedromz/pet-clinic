package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdatePetCommand {
    @NotNull(message = "PET_NAME_NOT_NULL")
    private String petName;
    @NotNull(message = "TYPE_NOT_NULL")
    private String type;
    @NotNull(message = "RACE_NOT_NULL")
    private String race;
    @Past
    @NotNull(message = "BIRTHDATE_NOT_NULL")
    private LocalDate birthDate;
    @NotNull(message = "OWNER_NAME_NOT_NULL")
    private String ownerName;
    @Email
    @NotNull(message = "OWNER_EMAIL_NOT_NULL")
    private String ownerEmail;
    @NotEmpty(message = "VERSION_NOT_EMPTY")
    private int version;
}
