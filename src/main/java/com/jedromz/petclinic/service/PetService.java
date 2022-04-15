package com.jedromz.petclinic.service;

import com.jedromz.petclinic.model.Pet;
import com.jedromz.petclinic.model.command.UpdatePetCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PetService {

    Page<Pet> findAll(Pageable pageable);

    Optional<Pet> findById(Long id);

    Pet save(Pet pet);

    void deleteById(Long id);

    Pet edit(Pet toEdit, UpdatePetCommand updatePetCommand);

    boolean existsByEmail(String email);

    void savePets(List<Pet> pets);

    boolean isAppointed(Pet pet, LocalDateTime dateTime);
}
