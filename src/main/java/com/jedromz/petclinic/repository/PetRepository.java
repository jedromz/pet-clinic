package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p left join fetch p.visits where p.id = ?1")
    Optional<Pet> findPetWithVisits(Long id);

    @Query("select p from Pet p left join fetch p.visits where p.id = ?1")
    Optional<Pet> findPetWithVisits(Long id, LockModeType lockModeType);

    boolean existsByOwnerEmail(String email);
}
