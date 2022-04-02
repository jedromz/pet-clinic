package com.jedromz.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Visit {

    private static final int TIME_IN_HOURS = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @Version
    private int version;

    @Builder
    public Visit(LocalDateTime dateTime, Vet vet, Pet pet) {
        this.dateTime = dateTime;
        this.vet = vet;
        this.pet = pet;
        vet.getVisits().add(this);
        pet.getVisits().add(this);
    }
}
