package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p left join fetch p.visits where p.id = ?1")
    Optional<Pet> findById(Long id);
}
