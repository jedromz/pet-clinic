package com.jedromz.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String petSpecialization;
    private BigDecimal rate;
    private String nip;
    @OneToMany(mappedBy = "pet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Visit> visits;

    @Version
    private int version;

    @Builder
    public Vet(String firstName, String lastName, String specialization, String petSpecialization, BigDecimal rate, String nip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.petSpecialization = petSpecialization;
        this.rate = rate;
        this.nip = nip;
    }
}
