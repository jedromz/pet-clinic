package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.Vet;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import com.jedromz.petclinic.model.command.UpdateVetCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VetService {

    Page<Vet> findAll(Pageable pageable);

    Optional<Vet> findById(Long id);

    Vet save(Vet vet);

    void deleteById(Long id);

    Vet edit(Vet toEdit, UpdateVetCommand updateVetCommand);

    Vet fire(Vet toEdit);

    boolean existsByNip(String nip);

    void deleteAll();
}
