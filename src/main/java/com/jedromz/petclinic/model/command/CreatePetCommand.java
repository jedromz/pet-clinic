package com.jedromz.petclinic.model.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CreatePetCommand {
    private String petName;
    private String type;
    private String race;
    private LocalDate birthDate;
    private String ownerName;
    private String ownerEmail;
}
