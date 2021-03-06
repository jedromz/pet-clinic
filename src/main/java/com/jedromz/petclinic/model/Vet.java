package com.jedromz.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"visits"})
@NoArgsConstructor
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String specialization;
    private String petSpecialization;
    private BigDecimal rate;
    private String nip;
    private boolean isFired;
    @OneToMany(mappedBy = "vet", cascade = {CascadeType.ALL})
    private Set<Visit> visits = new HashSet<>();

    @Version
    private int version;

    @Builder
    public Vet(String firstname, String lastName, String specialization, String petSpecialization, BigDecimal rate, String nip, boolean isFired) {
        this.firstname = firstname;
        this.lastname = lastName;
        this.specialization = specialization;
        this.petSpecialization = petSpecialization;
        this.rate = rate;
        this.nip = nip;
        this.isFired = isFired;
    }
}
