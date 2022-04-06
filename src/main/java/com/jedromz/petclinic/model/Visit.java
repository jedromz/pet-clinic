package com.jedromz.petclinic.model;

import com.jedromz.petclinic.error.ScheduleConflictException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
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
    @FutureOrPresent
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @Version
    private int version;
    private boolean confirmed;

    @Builder
    public Visit(LocalDateTime dateTime, Vet vet, Pet pet) {
        if (vet.isFree(dateTime) || pet.isAppointed(dateTime)) {
            throw new ScheduleConflictException();
        }
        this.dateTime = dateTime;
        this.vet = vet;
        this.pet = pet;
        vet.getVisits().add(this);
        pet.getVisits().add(this);

    }

    private boolean isFree(LocalDateTime dateTime, Vet vet) {
        return vet.getVisits().stream()
                .map(Visit::getDateTime)
                .anyMatch(visitTime -> visitTime.isEqual(dateTime));
    }

}
