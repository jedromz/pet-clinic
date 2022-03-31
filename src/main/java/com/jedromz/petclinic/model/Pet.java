package com.jedromz.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petName;
    private String species;
    private String race;
    private LocalDate birthDate;
    private String ownerName;
    private String ownerEmail;
    @OneToMany(mappedBy = "pet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Visit> visits;

    @Version
    private int version;

    @Builder
    public Pet(String petName, String species, String race, LocalDate birthDate, String ownerName, String ownerEmail) {
        this.petName = petName;
        this.species = species;
        this.race = race;
        this.birthDate = birthDate;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
    }
}
