package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UpdatePetCommand {

    private String petName;
    private String type;
    private String race;
    @Past
    private LocalDate birthDate;
    private String ownerName;
    @Email
    private String ownerEmail;
    private int version;
}
