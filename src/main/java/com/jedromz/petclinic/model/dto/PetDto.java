package com.jedromz.petclinic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PetDto extends RepresentationModel<PetDto> {

    private String petName;
    private String type;
    private String race;
    private LocalDate birthDate;
    private String ownerName;
    private String ownerEmail;
    private int version;
}
