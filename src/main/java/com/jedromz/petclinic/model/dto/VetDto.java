package com.jedromz.petclinic.model.dto;

import com.jedromz.petclinic.model.Visit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
public class VetDto {


    private String firstName;
    private String lastName;
    private String specialization;
    private String petSpecialization;
    private BigDecimal rate;
    private String nip;
    private int version;
}
