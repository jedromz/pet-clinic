package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface VetRepository extends JpaRepository<Vet, Long> {

    @Query("select v from Vet v left join fetch v.visits where v.id = ?1")
    Optional<Vet> findVetWithVisits(Long id);

    @Query("select v from Vet v left join fetch v.visits where v.id = ?1")
    Optional<Vet> findVetWithVisits(Long id, LockModeType lockModeType);

    boolean existsByNip(String nip);
}
