package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
