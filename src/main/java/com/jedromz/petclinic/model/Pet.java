package com.jedromz.petclinic.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"visits"})
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petName;
    private String type;
    private String race;
    @Past
    private LocalDate birthDate;
    private String ownerName;
    @Email
    private String ownerEmail;
    @OneToMany(mappedBy = "pet", cascade = {CascadeType.ALL})
    private Set<Visit> visits = new HashSet<>();

    @Version
    private int version;

    @Builder
    public Pet(String petName, String type, String race, LocalDate birthDate, String ownerName, String ownerEmail) {
        this.petName = petName;
        this.type = type;
        this.race = race;
        this.birthDate = birthDate;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
    }


    public boolean isAppointed(LocalDateTime dateTime) {
        return getVisits().stream()
                .map(Visit::getDateTime)
                .anyMatch(visitTime -> visitTime.isEqual(dateTime));
    }
}
