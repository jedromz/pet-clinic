package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VetRepository extends JpaRepository<Vet, Long> {

    @Query("select v from Vet v left join fetch v.visits where v.id = ?1")
    Optional<Vet> findById(Long id);

    @Query("select count(v)>0 from Vet v where v.nip = ?1")
    boolean existsByNip(String nip);
}
