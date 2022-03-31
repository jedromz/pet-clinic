package com.jedromz.petclinic.repository;

import com.jedromz.petclinic.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
