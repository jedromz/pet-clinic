package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    boolean existsByDateTimeAndVet_Id(LocalDateTime dateTime, Long id);

    boolean existsByDateTimeAndPet_Id(LocalDateTime dateTime, Long id);

    List<Visit> findByDateTimeIsBetweenAndVet_SpecializationAndVet_PetSpecialization(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String specialization, String petSpecialization);

}
