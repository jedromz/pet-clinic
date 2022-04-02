package com.jedromz.petclinic.model.dto;

import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.Visit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class VetDto extends RepresentationModel<VetDto> {


    private String firstName;
    private String lastName;
    private String specialization;
    private String petSpecialization;
    private BigDecimal rate;
    private String nip;
    private int version;
    private boolean isFired;
}
